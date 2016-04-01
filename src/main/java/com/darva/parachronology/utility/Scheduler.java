package com.darva.parachronology.utility;

import com.darva.parachronology.Parachronology;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


// Source code kindly supplied by tterrag.  Many thanks.
public class Scheduler {

    public Scheduler()
    {
        //FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static final class Task {
        public Task(int Delay, Runnable run, Side side)
        {
            this.delay=Delay;
            this.toRun=run;
            this.side=side;
        }
        private int delay;
        private Runnable toRun;
        private Side side;

        private boolean run() {
            if (delay <= 0) {
                toRun.run();
                return true;
            }
            delay--;
            return false;
        }
    }

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Schedules a task to be called later
     *
     * @param delay
     *          The amount of ticks to delay the call
     * @param task
     *          The {@link Runnable} to be run when the delay is up
     *
     * @see {@link #schedule(int, Runnable, Side)} for more details.
     */
    public void schedule(int delay, Runnable task) {
        schedule(delay, task, Side.SERVER);
    }

    /**
     * Schedules a task to be called later
     *
     * @param delay
     *          The amount of ticks to delay the call
     * @param task
     *          The {@link Runnable} to be run when the delay is up
     * @param side
     *          The side to schedule the task on.
     *          <p>
     *          You will get a different {@link TickEvent} depending on the side.
     *          <br>
     *          {@link Side#CLIENT} will be passed a {@link ClientTickEvent} <br>
     *          {@link Side#SERVER} will be passed a {@link ServerTickEvent}
     *          <p>
     *          Note: passing in {@link Side#CLIENT} on a dedicated server will
     *          work, but your task will never be called. Please avoid doing this
     *          to save processing.
     */
    public void schedule(int delay, Runnable task, Side side) {
        tasks.add(new Task(delay, task, side));
    }

    /**
     * Returns the {@link Scheduler} instance for the current side.
     *
     * @see {@link Scheduler#schedule(int, Runnable)} for scheduling information.
     *
     * @return The {@link Scheduler} instance.
     */
    public static Scheduler instance() {
        return Parachronology.proxy.getScheduler();
    }

    /**
     * For internal use only. Do not call.
     */
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            runTasks(Side.SERVER);
        }
    }

    /**
     * For internal use only. Do not call.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
//        if (event.phase == TickEvent.Phase.END) {
//            runTasks(Side.CLIENT);
//        }
    }

    private void runTasks(Side side) {
        Iterator<Task> iter = tasks.iterator();
        ArrayList<Task> toRemove = new ArrayList<Task>();
        while (iter.hasNext()) {
            Task next = iter.next();
            if (next.side == side && next.run()) {
                toRemove.add(next);
            }
        }
        for(Task task : toRemove)
        {
            tasks.remove(task);
        }
        toRemove.clear();
    }
}
