package cn.promptness.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回前台数据类型
 * 
 * @author Lynn
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult{
	
	private int code;
	private String message;
	
}
