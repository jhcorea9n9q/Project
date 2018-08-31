package com.java.web.hadoop;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.springframework.stereotype.Service;

import com.java.web.util.HttpUtil;

@Service
public class HadoopService implements HadoopServiceInterface {
	
	@Resource(name="hdConf")
	Configuration conf;

	@Override
	public HashMap<String, Object> hadoop(HttpServletRequest req) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		String operation = param.get("operation").toString();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_hhmmss");
		
		Job job = Job.getInstance(conf, "Hadoop");
		URI inputUri = URI.create("/input/movies.csv");
		URI outputUri = URI.create("/result/" + operation + "/" + date.format(new Date()) );
		Path resultPath = new Path(outputUri);
		FileSystem f = FileSystem.get(outputUri, conf);
		if(f.exists(resultPath)) {
		   f.delete(resultPath, true);
		}
		FileInputFormat.addInputPath( job, new Path(inputUri) );
		FileOutputFormat.setOutputPath( job, new Path(outputUri) );
		job.setJarByClass(this.getClass());
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		if(operation.equals("G")) {
			job.setMapperClass(Project_G_Mapper.class);
		}else if(operation.equals("C")) {
			job.setMapperClass(Project_C_Mapper.class);
		}else if(operation.equals("Y")) {
			job.setMapperClass(Project_Y_Mapper.class);
		}
		job.setReducerClass(ProjectReducer.class);
		job.waitForCompletion(true);
		resultMap.put("path", outputUri.toString() );
		return resultMap;
	}

	@Override
	public HashMap<String, Object> callData(HttpServletRequest req) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		String filepath = param.get("path").toString();
		URI uri = URI.create(filepath + "/part-r-00000");
		Path path = new Path(uri);
		FileSystem file = FileSystem.get(uri, conf);
		FSDataInputStream fsis = file.open(path);
		byte[] buffer = new byte[50000];
		int byteRead = 0;
		String result = "";
		while((byteRead = fsis.read(buffer)) > 0) { 
			result = new String(buffer, 0, byteRead);
		}
		String[] rows = result.split("\n");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for(int i = 0; i < rows.length; i++) {
			String row = rows[i];
			String[] cols = row.split("\t");
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int c = 0; c < cols.length; c++) {
				map.put(c + "", cols[c]);
			}
			list.add(map);
		}
		resultMap.put("result", list);
		return resultMap;
	}

}
