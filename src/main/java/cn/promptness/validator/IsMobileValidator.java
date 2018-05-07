package cn.promptness.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cn.promptness.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lynn
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;
	
	@Override
	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
