package pers.syq.fastadmin.backstage.annotation.sensitive;

import com.github.houbb.sensitive.api.IContext;
import com.github.houbb.sensitive.api.IStrategy;

public class FastAdminPasswordStrategy implements IStrategy {
    @Override
    public Object des(Object o, IContext iContext) {
        return "*****";
    }
}
