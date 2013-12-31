package com.github.ddth.plommon;

import play.Application;
import play.Plugin;

import com.github.ddth.plommon.bo.BaseDao;

/**
 * Activate plommon module by adding the following line to {@code play.plugins}
 * file:
 * 
 * <p>
 * {@code 1000:com.github.ddth.plommon.Activator}
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.4.5
 */
public class Activator extends Plugin {
    private final Application application;

    public Activator(Application application) {
        this.application = application;
    }

    @Override
    public void onStart() {
        super.onStart();
        BaseDao.init();
    }

    @Override
    public void onStop() {
        try {
            // EMPTY
        } finally {
            super.onStop();
        }
    }
}
