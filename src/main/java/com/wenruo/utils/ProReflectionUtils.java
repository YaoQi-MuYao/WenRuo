package com.wenruo.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * 反射工具类.
 * 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 * @author calvin
 * @version 2013-01-15
 */
@SuppressWarnings("rawtypes")
public class ProReflectionUtils {
	
	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";
	
	private static Logger logger = LoggerFactory.getLogger(ProReflectionUtils.class);

	/**
	 * 调用Getter方法.
	 * 支持多级，如：对象名.对象名.方法
	 */
	public static Object invokeGetter(Object obj, String propertyName) {
		Object object = obj;
		for (String name : StringUtils.split(propertyName, ".")){
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}

	/**
	 * 调用Setter方法, 仅匹配方法名。
	 * 支持多级，如：对象名.对象名.方法
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i=0; i<names.length; i++){
			if(i<names.length-1){
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			}else{
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			logger.error("Could not find field [" + fieldName + "] on target [" + obj + "]");
			return null;
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			logger.error("Could not find field [" + fieldName + "] on target [" + obj + "]");
			return;
			//throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, convert(value, field.getType()));
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}
	
	public static Object convert(Object object, Class<?> type) {
	    if (object instanceof Number) {
	        Number number = (Number) object;
	        if (type.equals(byte.class) || type.equals(Byte.class)) {
	            return number.byteValue();
	        }
	        if (type.equals(short.class) || type.equals(Short.class)) {
	            return number.shortValue();
	        }
	        if (type.equals(int.class) || type.equals(Integer.class)) {
	            return number.intValue();
	        }
	        if (type.equals(long.class) || type.equals(Long.class)) {
	            return number.longValue();
	        }
	        if (type.equals(float.class) || type.equals(Float.class)) {
	            return number.floatValue();
	        }
	        if (type.equals(double.class) || type.equals(Double.class)) {
	            return number.doubleValue();
	        }
	    }
	    if(type.equals(String.class)){
	    	return object==null?"":object.toString();
	    }
	    return object;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
	 * 同时匹配方法名+参数类型，
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符，
	 * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 匹配函数名+参数类型。
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
	 * 如向上转型到Object仍无法找到, 返回null.
	 * 只匹配函数名。
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
	
	public static Class<?> getUserClass(Object instance) {
		Assert.notNull(instance, "Instance must not be null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}
	
	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * @Description: 递归回去所有field ， 包括父类的
	 * @param: [clazz]
	 * @return: java.util.List<java.lang.reflect.Field>
	 * @author: yupeng.yang
	 * @Date: 2019/3/27 16:46
	 */
	public static List<Field> getAllField(Class clazz){
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null){
			Field[] fields = clazz.getDeclaredFields();
			fieldList.addAll(Arrays.asList(fields));
			clazz = clazz.getSuperclass();
		}
		return fieldList;
	}

	public static boolean hasField(Class clazz , String fieldName){
		List<Field> allField = getAllField(clazz);
		Set<String> fieldSet = allField.stream().map(Field::getName).collect(toSet());
		return fieldSet.contains(fieldName);
	}

	public static Field getField(Class clazz , String name){
		try {
			List<Field> fieldList = getAllField(clazz);
			for (Field field : fieldList) {
				String fieldName = field.getName();
				if(fieldName.equals(name)){
					return field;
				}
			}
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @Description: 获取类注解
	 * @param: [aClass, annotationClazz]
	 * @return: java.lang.annotation.Annotation
	 * @author: yupeng.yang
	 * @Date: 2019/3/27 16:59
	 */
	public static Annotation getAnnotation(Class<?> aClass, Class<? extends Annotation> annotationClazz) {
		if (aClass.isAnnotationPresent(annotationClazz)) {
			Annotation annotation = aClass.getAnnotation(annotationClazz);
			return annotation;
		}
		return null;
	}

	/**
	 * @Description: 获取属性注解
	 * @param: [field, annotationClazz]
	 * @return: java.lang.annotation.Annotation
	 * @author: yupeng.yang
	 * @Date: 2019/3/27 16:59
	 */
	public static Annotation getAnnotation(Field field,Class<? extends Annotation> annotationClazz) {
		if (field.isAnnotationPresent(annotationClazz)) {
			Annotation annotation = field.getAnnotation(annotationClazz);
			return annotation;
		}
		return null;
	}

	/**
	 * 通过字段名从对象或对象的父类中得到字段的值（调用字典的get方法，可以取出复杂的对象的值）
	 * @param object 对象实例
	 * @param fieldName 字段名
	 * @return 字段对应的值
	 * @throws Exception
	 * @Author : ll. create at 2016年4月14日 上午9:18:19
	 */
	public static Object getValueOfGetIncludeObjectFeild(Object object, String fieldName)
			throws Exception {

		if (object == null) {
			return null;
		}
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		if("com.alibaba.fastjson.JSONObject".equals(object.getClass().getName())){
			//json数据
			JSONObject jsonObject = (JSONObject) object;
			if(jsonObject.containsKey(fieldName)){
				return jsonObject.get(fieldName);
			}
		}
		Field field = null;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				if (fieldName.contains(".")) {
					// 如：operatorUser.name、operatorUser.org.name，递归调用
					String[] splitFiledName = fieldName.split("\\.");
					return getValueOfGetIncludeObjectFeild(
							getValueOfGetIncludeObjectFeild(object, splitFiledName[0]),
							splitFiledName[1]);
				}
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);

				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				//获得get方法
				Method getMethod = pd.getReadMethod();
				//执行get方法返回一个Object
				return getMethod.invoke(object);
			} catch (Exception e) {
				//这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				//如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}

		return null;
	}
}
