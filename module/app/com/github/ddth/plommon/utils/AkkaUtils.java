package com.github.ddth.plommon.utils;

import java.util.concurrent.TimeUnit;

import play.libs.Akka;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.Scheduler;

/**
 * Akka-related utilities.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.4.7
 */
public class AkkaUtils {

    public static ActorSystem actorSystem() {
        return Akka.system();
    }

    public static Scheduler scheduler() {
        return actorSystem().scheduler();
    }

    public static ActorRef actorOf(Class<? extends Actor> actorClass) {
        return actorSystem().actorOf(Props.create(actorClass));
    }

    /**
     * Schedules a job once.
     * 
     * @param actorClass
     * @param initialDelay
     * @param initialDelayTimeUnit
     * @param message
     * @return
     */
    public static Cancellable scheduleOnce(Class<? extends Actor> actorClass, long initialDelay,
            TimeUnit initialDelayTimeUnit, Object message) {
        ActorRef actorRef = actorOf(actorClass);
        FiniteDuration initialDuration = initialDelay != 0 ? Duration.create(initialDelay,
                initialDelayTimeUnit) : Duration.Zero();
        return scheduler().scheduleOnce(initialDuration, actorRef, message,
                actorSystem().dispatcher(), null);
    }

    /**
     * Schedules a job.
     * 
     * @param actorClass
     * @param initialDelay
     * @param initialDelayTimeUnit
     * @param repeatedDelay
     * @param repeatedDelayTimeUnit
     * @param message
     * @return
     */
    public static Cancellable schedule(Class<? extends Actor> actorClass, long initialDelay,
            TimeUnit initialDelayTimeUnit, long repeatedDelay, TimeUnit repeatedDelayTimeUnit,
            Object message) {
        ActorRef actorRef = actorOf(actorClass);
        FiniteDuration initialDuration = initialDelay != 0 ? Duration.create(initialDelay,
                initialDelayTimeUnit) : Duration.Zero();
        FiniteDuration repeatedDuration = repeatedDelay != 0 ? Duration.create(repeatedDelay,
                repeatedDelayTimeUnit) : Duration.Zero();
        return scheduler().schedule(initialDuration, repeatedDuration, actorRef, message,
                actorSystem().dispatcher(), null);
    }
}
