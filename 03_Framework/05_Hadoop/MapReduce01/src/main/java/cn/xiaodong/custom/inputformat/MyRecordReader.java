package cn.xiaodong.custom.inputformat;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * 负责将整个文件转化成一组Key Value对
 * key是文件的路径名称
 * value是读取的二进制流
 */
public class MyRecordReader extends RecordReader<Text, BytesWritable> {


    //表示文件读完了，默认是false，表示文件没读过
    private boolean isRead = false;

    //kv对
    private Text key = new Text();
    private BytesWritable value = new BytesWritable();

    private FSDataInputStream inputStream;
    private FileSplit fs;

    /**
     * 初始化方法，一般执行一些初始化操作
     * @param split
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //开流
        fs = (FileSplit) split;

        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        inputStream = fileSystem.open(fs.getPath());
    }

    /**
     * 关闭方法，一般用来关闭资源
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        //关流
        IOUtils.closeStream(inputStream);
    }

    /**
     * 读取下一组Key Value Pair
     * @return 是否读到
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!isRead) {
            //读取这个文件，用流
            //填充key
            key.set(fs.getPath().toString());

            //填充Value
            byte[] buffer = new byte[(int) fs.getLength()];
            int read = inputStream.read(buffer);

            value.set(buffer, 0, buffer.length);

            //标记文件读完
            isRead = true;
            return true;
        } else {
            return false;

        }
    }

    /**
     * 获取当前读到的Key
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    /**
     * 获取当前读到的Value
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    /**
     * 显示进度
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return isRead ? 1 : 0;
    }


}
