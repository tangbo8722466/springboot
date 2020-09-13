package com.springboot.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EasyExcelUtils
 * @Date 2019/1/30 下午11:59
 * @Description 封装的EasyExcel导出工具类
 * @Version 1.0
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * @Author
     * @Description 导出excel 支持一张表导出多个sheet
     * @Param OutputStream 输出流
     * Map<String, List>  sheetName和每个sheet的数据
     * ExcelTypeEnum 要导出的excel的类型 有ExcelTypeEnum.xls 和有ExcelTypeEnum.xlsx
     * @Date 上午12:16 2019/1/31
     */
    public static void createExcelStreamMutilByEaysExcel(HttpServletResponse response, Map<String, List<? extends BaseRowModel>> SheetNameAndDateList, ExcelTypeEnum type,String fileName) throws UnsupportedEncodingException {
        if (checkParam(SheetNameAndDateList, type)) {return;}
        try {
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + type.getValue());
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, type, true);
            setSheet(SheetNameAndDateList, writer);
            writer.finish();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author
     * @Description //setSheet数据
     * @Date 上午12:39 2019/1/31
     */
    private static void setSheet(Map<String, List<? extends BaseRowModel>> SheetNameAndDateList, ExcelWriter writer) {
        int sheetNum = 1;
        for (Map.Entry<String, List<? extends BaseRowModel>> stringListEntry : SheetNameAndDateList.entrySet()) {
            Sheet sheet = new Sheet(sheetNum, 0, stringListEntry.getValue().get(0).getClass());
            sheet.setSheetName(stringListEntry.getKey());
            sheet.setAutoWidth(true);
            writer.write(stringListEntry.getValue(), sheet);
            sheetNum++;

        }
    }

    /**
     * 复杂的填充
     *
     * @since 2.1.1
     */
    @Test
    public static void fillExcelStreamMutilByEaysExcel(HttpServletResponse response, List<? extends BaseRowModel> SheetNameAndDateList, Map<String, Object> map, ExcelTypeEnum type, String fileName) throws IOException {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        //String templateFileName = EasyExcelUtils.class.getClass().getResource("/template/templates.xlsx").getPath();
        //InputStream templateFileName = EasyExcelUtils.class.getClassLoader().getResourceAsStream("/templates/template.xlsx");
        InputStream templateFileName = new ClassPathResource("templates/template.xlsx").getInputStream();
        log.info("teamplate file {}", templateFileName.available());
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + type.getValue());
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(SheetNameAndDateList, fillConfig, writeSheet);
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
    }


    /**
     * @Author
     * @Description 校验参数
     * @Date 上午12:39 2019/1/31
     */
    private static boolean checkParam(Map<String, List<? extends BaseRowModel>> SheetNameAndDateList, ExcelTypeEnum type) {
        if (CollectionUtils.isEmpty(SheetNameAndDateList)) {
//            log.error("SheetNameAndDateList不能为空");
            return true;
        } else if (type == null) {
//            log.error("导出的excel类型不能为空");
            return true;
        }
        return false;
    }
}
