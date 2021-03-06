//package com.wenruo;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.SocketChannel;
//import java.util.Iterator;
//
///**
// * @deprecated: NIO客户端，暂时记录在该项目里面，后续在写进独立的项目中
// * @author: MuYao
// * @date: Created in 2020/4/30 23:43
// * @version: 1.0.0
// */
//@SpringBootTest
//public class SupplierTest {
//
//    //通道管理器
//    private Selector selector;
//
//    /**
//     * 获得一个Socket通道，并对该通道做一些初始化的工作
//     * @param: [ip：连接的服务器的ip , port：连接的服务器的端口号  ]
//     * @return: void
//     * @author: MuYao.Zhang
//     * @date: 2020/4/30 23:47
//     **/
//    public void initClient(String ip, int port) throws IOException {
//        // 获得一个Socket通道
//        SocketChannel channel = SocketChannel.open();
//        // 设置通道为非阻塞
//        channel.configureBlocking(false);
//        // 获得一个通道管理器
//        this.selector = Selector.open();
//
//        // 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
//        //用channel.finishConnect();才能完成连接
//        channel.connect(new InetSocketAddress(ip, port));
//        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
//        channel.register(selector, SelectionKey.OP_CONNECT);
//    }
//
//    /**
//     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
//     * @param: []
//     * @return: void
//     * @author: MuYao.Zhang
//     * @date: 2020/4/30 23:53
//     **/
//    @SuppressWarnings("unchecked")
//    public void listen() throws IOException {
//        // 轮询访问selector
//        while (true) {
//            selector.select();
//            // 获得selector中选中的项的迭代器
//            Iterator ite = this.selector.selectedKeys().iterator();
//            while (ite.hasNext()) {
//                SelectionKey key = (SelectionKey) ite.next();
//                // 删除已选的key,以防重复处理
//                ite.remove();
//                // 连接事件发生
//                if (key.isConnectable()) {
//                    SocketChannel channel = (SocketChannel) key
//                            .channel();
//                    // 如果正在连接，则完成连接
//                    if (channel.isConnectionPending()) {
//                        channel.finishConnect();
//                    }
//
//                    // 设置成非阻塞
//                    channel.configureBlocking(false);
//
//                    //在这里可以给服务端发送信息哦
//                    channel.write(ByteBuffer.wrap(new String("向服务端发送了一条信息").getBytes()));
//                    //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
//                    channel.register(this.selector, SelectionKey.OP_READ);
//
//                    // 获得了可读的事件
//                } else if (key.isReadable()) {
//                    read(key);
//                }
//            }
//        }
//    }
//
//    /**
//     *
//     * @param: [key]
//     * @return: void
//     * @author: MuYao.Zhang
//     * @date: 2020/4/30 23:55
//     **/
//    public void read(SelectionKey key) throws IOException{
//        SocketChannel channel = (SocketChannel) key.channel();
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        channel.read(buffer);
//        byte[] data = buffer.array();
//        String msg = new String(data).trim();
//        System.out.println("服务端收到信息："+msg);
//        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
//        channel.write(outBuffer);
//    }
//
//    /**
//     * 客户端测试主类
//     * @param: []
//     * @return: void
//     * @author: MuYao.Zhang
//     * @date: 2020/4/30 23:54
//     **/
//    @Test
//    public void supplierMain() throws IOException {
//
//        this.initClient("localhost",8000);
//        this.listen();
//    }
//}
