package com.bassiuz.gameforcehub.tools;

import com.bassiuz.gameforcehub.WERFiles.WerFile;

import java.util.ArrayList;

public class LocalRepository<E> extends ArrayList<E> {

    public E save(E object, E original)
    {
        if (original != null)
        {
            set(this.indexOf(original), object);
        }
        else
        {
            add(object);
        }

        return object;
    }

}
