/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class ThreadRenderer extends Thread {
    
    private static final int DELAY = 33;
    private static long rendererCounter = 0;
    private boolean stopped;
    private Component target;

    public ThreadRenderer(Component target) {
        this.stopped = false;
        this.target = target;
    }

    public void end() {
        stopped = true;
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long curTime;
        while (!stopped) {
            curTime = System.currentTimeMillis();
            while (curTime - lastTime < DELAY) {
                try {
                    Thread.sleep(DELAY - (curTime - lastTime));
                } catch (InterruptedException ex) {
                    Logger.getLogger(NPPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                curTime = System.currentTimeMillis();
            }
            lastTime = curTime;
            rendererCounter++;
            target.repaint();

        }
    }
    
    public static long rendererCounter(){
        return rendererCounter;
    }

}
