
/**
 * 对象审批状态转换
 * @param code(0：待审核，A：通过，R：拒绝，CC：复核中，CR：复核不通过，CA：复核通过，CT：复核终止)
 * @returns
 */
function getReviewStatus(code){
	if("0" == code){
		return "待审核";
	}else if("A" == code){
		return "通过";
	}else if("R" == code){
		return "拒绝";
	}else if("CC" == code){
		return "复核中";
	}else if("CR" == code){
		return "复核不通过";
	}else if("CA" == code){
		return "复核通过";
	}else if("CT" == code){
		return "复核终止";
	}else if("Z" == code){
		return "待提交";
	}else{
		return code;
	}
}