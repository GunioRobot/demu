package vm.util;

import vm.register.EFlags;

public class FlagCheck{
    public static void addCheck(long target, long value, long result, EFlags eflags, int bits){
        long starget = target & (1 << (bits - 1));
        long svalue = value & (1 << (bits - 1));
        long sresult = result & (1 << (bits - 1));

        eflags.setOverflow(starget == svalue && starget != sresult);
        FlagCheck.check(target, result, eflags, bits);
    }

    public static void subCheck(long target, long value, long result, EFlags eflags, int bits){
        long starget = target & (1 << (bits - 1));
        long svalue = value & (1 << (bits - 1));
        long sresult = result & (1 << (bits - 1));

        eflags.setOverflow(starget != svalue && starget != sresult);

        FlagCheck.check(target, result, eflags, bits);
    }

    public static void check(long before, long result, EFlags eflags, int bits){
        boolean flag = (result & ((long)1 << bits)) > 0/* || (before > 0) && (result > 0) && (before >= result) || (before < 0) && (result < 0) && (before <= result)*/;
        eflags.setCarry(flag);

        flag = (result == 0);
        eflags.setZero(flag);

        flag = result < 0;
        eflags.setSign(flag);

        flag = ((before & 0x0F) == 0x0F && (result & 0x0F) != 0x0F) || ((before & 0x0F) != 0x0F && (result & 0x0F) == 0x0F);
        eflags.setAdjust(flag);

        int count = (int)result & 0x01;
        count += (result & 0x02) >> 1;
        count += (result & 0x04) >> 2;
        count += (result & 0x08) >> 3;
        count += (result & 0x10) >> 4;
        count += (result & 0x20) >> 5;
        count += (result & 0x40) >> 6;
        count += (result & 0x80) >> 7;

        flag = (count % 2) == 0;
        eflags.setParity(flag);
    }
}