package me.declipsonator.particleblocker.utils;

import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class idComparator implements Comparator<Identifier> {

    // Function to compare
    public int compare(Identifier i1, Identifier i2) {
        if(i1.toString().equals(i2.toString())) return 0;
        String[] ids = {i1.toString(), i2.toString()};
        Arrays.sort(ids, Collections.reverseOrder());

        if (ids[0].equals(i1.toString()))
            return 1;
        else
            return -1;
    }
}