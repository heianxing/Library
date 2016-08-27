package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;

import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.rule.StrLen;

public class StrLenBean extends Rule {

    public String getValidateRule(Annotation ruleAnno) {
        String ruleName = StrLen.class.getSimpleName();
        StrLen anno = ((StrLen) ruleAnno);
        return ruleName + Consts.L_BRACE + anno.minLen() + Consts.DBC_SPLIT + anno.maxLen()
                + Consts.R_BRACE;
    }

    public boolean validate(Object... params) {
        String fieldVal = params[0].toString();

        if (fieldVal == null) {
            return true;
        }

        int minLen = Integer.parseInt(params[1].toString());
        int maxLen = Integer.parseInt(params[2].toString());
        return (minLen <= fieldVal.length() && fieldVal.length() <= maxLen);
    }

    public String getDefaultMsg(Annotation ruleAnno) {
        StrLen anno = ((StrLen) ruleAnno);
        int minLen = anno.minLen();
        int maxLen = anno.maxLen();

        StringBuilder msg = new StringBuilder("长度必须");
        if (minLen != 0) {
            msg.append("大于" + minLen);
        }
        if (maxLen != Integer.MAX_VALUE) {
            msg.append("小于" + maxLen);
        }
        return msg.toString();
    }

}
