package me.jeekhan.leyi.tags;
/**
 * EL函数
 * @author Jee Khan
 *
 */
public class Functions {
	/**
	 * 将浮点数向上取整
	 * @param num
	 * @return
	 */
	public static int cell(String num){
		try{
			Double d = Double.parseDouble(num);
			if(d>d.intValue()){
				return d.intValue() +1;
			}else{
				return d.intValue();
			}
		}catch(Exception e){
			return 0;
		}
	}

}
