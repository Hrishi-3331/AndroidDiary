package com.hrishi_3331.devstudio3331.diary;

public class Avatar {

    private int[] avtars = {R.drawable.male_avtar_1, R.drawable.male_avtar_2, R.drawable.male_avtar_3,
            R.drawable.female_avtar_1, R.drawable.female_avtar_2, R.drawable.female_avtar_3};

    public Avatar() {
    }

    public int getAvtar(int index){
        return avtars[index];
    }
}
