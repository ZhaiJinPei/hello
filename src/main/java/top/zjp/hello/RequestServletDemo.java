package top.zjp.hello;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/request/*")
@Slf4j
public class RequestServletDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求URL
        String uri = req.getRequestURI();
        StringBuffer requestURL = req.getRequestURL();
        log.info(uri);
        log.info(String.valueOf(requestURL));


        int position = uri.lastIndexOf("/");
        String method = uri.substring(position + 1);
        switch (method) {
            case "demo1": {
                this.demo1(req, resp);
            }
            case "demo2": {
                this.demo2(req, resp);
            }
            case "demo3": {
                this.demo3(req, resp);
            }
            case "demo4": {
                this.demo4(req, resp);
            }
            case "demo5": {
                this.demo5(req, resp);
            }
            case "demo6": {
                this.demo6(req, resp);
            }
            case "demo7": {
                this.demo7(req, resp);
            }
            case "demo8": {
                this.demo8(req, resp);
            }
            case "download": {
                this.download(req, resp);
            }
        }
    }


    /**
     * HttpServletRequest 获取请求行数据
     *
     * @param req  请求对象
     * @param resp 响应对象
     * @throws ServletException servlet异常
     * @throws IOException      io异常
     */
    private void demo1(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的方式
        resp.sendRedirect("index.html");
        String method = req.getMethod();
        log.info("\n---------------获取请求的方式-----------------\n" + method);
    }

    private void demo2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取 servlet 路径
        String servletPath = req.getServletPath();
        log.info("\n--------------获取 servlet 路径------------------\n" + servletPath);
    }

    private void demo3(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取虚拟目录
        String contextPath = req.getContextPath();
        log.info("\n-------------获取虚拟目录-------------------\n" + contextPath);
    }

    private void demo4(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String queryString = req.getQueryString();
        log.info("\n---------------请求参数分割-----------------\n" + queryString);
        //请求参数分割
        String[] s = queryString.split("&");
        log.info(s[0]);
        log.info(s[1]);
        //二次分割
        String[] s1 = s[0].split("=");
        log.info("\n-------------------二次分割----------------------\n" + s1[0]);
        log.info(s1[1]);
        String[] s2 = s[1].split("=");
        log.info(s2[0]);
        log.info(s2[1]);
    }

    private void demo5(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求URL
        String contextPath = req.getContextPath();
        log.info("\n----------------获取请求URL------------------\n" + contextPath);
    }

    private void demo6(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取协议及版本
        String protocol = req.getProtocol();
        log.info("\n---------------获取协议及版本-----------------\n" + protocol);
    }

    private void demo7(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取客户机的IP地址
        String remoteAddr = req.getRemoteAddr();
        log.info("\n----------------获取客户机的IP地址----------------\n" + remoteAddr);
    }

    private void demo8(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        通过 HttpServlet对象获取 ServletContext对象
        ServletContext context = this.getServletContext();
//         webapp目录下资源访问
        String b = context.getRealPath("/b.txt");
        log.info("\n-------------- webapp目录下资源访问------------------\n" + b);
// WEB-INF目录下的资源访问
        String c = context.getRealPath("/WEB-INF/c.txt");
        log.info("\n----------------- WEB-INF目录下的资源访问---------------\n" + c);
// src目录下的资源访问
        String a = context.getRealPath("/WEB-INF/classes/a.txt");
        log.info("\n--------------- src目录下的资源访问-----------------\n" + a);
    }

    private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//获取请求参数，文件名称
        String filename = req.getParameter("filename");
//找到文件服务器路径
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/" + filename);
//用字节流关联
        FileInputStream fis = new FileInputStream(realPath);
//获取文件的mime类型
        String mimeType = servletContext.getMimeType(filename);

        resp.setHeader("content-type", mimeType);

        resp.setHeader("content-disposition", "attachment;filename=" +
                filename);
//将输入流的数据写出到输出流中
        ServletOutputStream sos = resp.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len;
        while ((len = fis.read(buff)) != -1) {
            sos.write(buff, 0, len);
        }
        fis.close();
        log.info("\n----------------文件下载----------------\n");
    }
}