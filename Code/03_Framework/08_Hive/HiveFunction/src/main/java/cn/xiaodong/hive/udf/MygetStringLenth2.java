package cn.xiaodong.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * 新接口，给一个字符串，返回字符串长度
 *
 * @author: xiaodong
 * @create: 2021-05-20 07:31
 **/
public class MygetStringLenth2 extends GenericUDF {

    /**
     * 对输入的方法做检查，以及约束输出的类型
     *
     * @param arguments 输入参数的检查器
     * @return 输出的参数检查器
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 1) {
            throw new UDFArgumentLengthException("函数入参的数量不正确，仅支持一个入参！");
        }

//        if (arguments[0].getTypeName().equals("String")) {
//            throw new UDFArgumentTypeException(0, "函数入参仅支String，不支持" + arguments[0].getTypeName() + "!");
//        }

        if (arguments[0].getCategory().equals(ObjectInspector.Category.PRIMITIVE)) {
            throw new UDFArgumentTypeException(0, "函数入参仅支String，不支持" + arguments[0].getTypeName() + "!");
        }
        // 检查输出类型
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    /**
     * 真正的实现逻辑
     *
     * @param arguments
     * @return
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Object o = arguments[0].get();
        if (o == null) {
            return 0;
        }

        return o.toString().length();
    }

    /**
     * 如果函数报错，错误提示
     * @param children
     * @return
     */
    @Override
    public String getDisplayString(String[] children) {
        return "执行发生了错误";
    }
}
