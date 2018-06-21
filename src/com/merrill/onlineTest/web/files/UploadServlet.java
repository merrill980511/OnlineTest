package com.merrill.onlineTest.web.files;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.merrill.onlineTest.dao.IQuestionDAO;
import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.dao.impl.QuestionDAOImpl;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.dao.impl.TestPaperDAOImpl;
import com.merrill.onlineTest.domain.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/upload")
public class UploadServlet extends HttpServlet {
//    private static final String ALLOWED_EXCEL_TYPE = "xls;xlsx";
    ISortDAO sortDAO;
    IQuestionDAO questionDAO;
    ITestPaperDAO testPaperDAO;

    @Override
    public void init() throws ServletException {
        sortDAO = new SortDAOImpl();
        questionDAO = new QuestionDAOImpl();
        testPaperDAO = new TestPaperDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("check".equals(cmd)){
            check(req, resp);
        } if ("sort".equals(cmd)){
           sort(req, resp);
        } else {
            ISortDAO dao = new SortDAOImpl();
            List<Sort> sorts = dao.list();
            req.setAttribute("sorts", sorts);
            System.out.println("sorts        " +sorts);
            req.getRequestDispatcher("/WEB-INF/views/admin/question/upload.jsp").forward(req, resp);
        }
    }

    protected void sort(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("sort", req.getParameter("sort"));
        req.getRequestDispatcher("/WEB-INF/views/admin/question/file.jsp").forward(req, resp);
    }

    protected void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("upload");
        //解析和检查请求，请求方法是POST，请求编码是mulitipart/form-data
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            return;
        }
        try {
            //1.创建FileItemFactory对象
            //FileItemFactory是用来创建FileItem对象
            //FileItem：form表单中的菜单控制的封装
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024); //设置缓存大小
            //2.创建文件上传处理器
            //本身没有创建FileItem的关系 是一个包含关系
            ServletFileUpload upload = new ServletFileUpload(factory);
            //3.解析请求
            List<FileItem> items = upload.parseRequest(req);
            //4.迭代出每一个FileItem
            for (FileItem item :
                    items) {
                //获取表单属性的name属性
                String fieldName = item.getFieldName();
                //getFieldName 属性名
                //getName 上传的文件名
                if (item.isFormField()) {
                    //普通表单控件 获取普通表单控件的参数值
                    String value = item.getString("utf-8");
                    System.out.println(fieldName + "-" + value);
                } else {
                    //当前上传文件的mime类型
                    String mimeType = super.getServletContext().getMimeType(item.getName());
                    System.out.println(mimeType);

                    //文件拓展名
                    String ext = FilenameUtils.getExtension(item.getName());
//                    String[] allowed = ALLOWED_EXCEL_TYPE.split(";");
                    //当前上传的文件格式不在允许的范围内
                    if (!"xlsx".equals(ext)) {
                        req.setAttribute("errorMsg", "请上传Excel文件！");
                        req.getRequestDispatcher("/WEB-INF/views/admin/editResult.jsp").forward(req, resp);
                        return;
                    }

                    //表单上传挂件   把二进制数据写到哪一个文件中去
                    String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(item.getName());
                    String dir = getServletContext().getRealPath("/WEB-INF/upload");
                    System.out.println(fileName);
                    System.out.println(dir);
                    item.write(new File(dir, fileName));

//                    String s = req.getParameter("sort");
                    String s = (String) req.getSession().getAttribute("sort");
                    Sort sort = new Sort();
                    if (StringUtils.isNotBlank(s)){
                        sort = sortDAO.getSortById(new Long(s));
                    }
                    System.out.println(s);

                    String path = super.getServletContext().getRealPath("/WEB-INF/upload/" + fileName);
                    File file = new File(path);
                    FileInputStream inputStream = new FileInputStream(file);
                    //读取工作簿
                    XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
                    //读取工作表
                    XSSFSheet sheet = workBook.getSheetAt(0);

                    for(int i=0;i<=sheet.getLastRowNum();i++){
                        Row row=sheet.getRow(i);
                        Question question = new Question();
                        List<Option> options = new ArrayList<>();
                        question.setOptions(options);
                        question.setSort(sort);
                        Cell cell = row.getCell(0);
                        question.setStem(cell.getStringCellValue().trim());
                        char c = 'a';
                        for (int j = 1; j < row.getLastCellNum()-1; j++) {
                            cell = row.getCell(j);
                            Option option = new Option();
                            options.add(option);
                            option.setItem(String.valueOf(c));
                            c++;
                            option.setContent(cell.getStringCellValue().trim());
                        }
                        cell = row.getCell(row.getLastCellNum()-1);
                        String answer = cell.getStringCellValue().trim();
                        question.setAnswer(answer);
                        Selection selection = new Selection();
                        question.setSelection(selection);
                        if (answer.length() == 1){
                            selection.setId(new Long(1));
                        } else {
                            selection.setId(new Long(2));
                        }
                        System.out.println("question    "+question);
                        questionDAO.save(question);
                    }
//                    //读取行
//                    HSSFRow row = sheet.getRow(2);
//                    //读取单元格
//                    HSSFCell cell = row.getCell(2);
//                    String value = cell.getStringCellValue();


                    inputStream.close();
                    workBook.close();//最后记得关闭工作簿

                    file.delete();


                    System.out.println(item.isInMemory());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
