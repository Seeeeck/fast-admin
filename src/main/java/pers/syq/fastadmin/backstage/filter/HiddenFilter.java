package pers.syq.fastadmin.backstage.filter;

public class HiddenFilter {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Boolean){
            return !(Boolean)obj;
        }
        return false;
    }
}
