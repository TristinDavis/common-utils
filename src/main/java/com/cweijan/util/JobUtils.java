package com.cweijan.util;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;

import com.cweijan.constant.JobType;

public class JobUtils {

	private static Scheduler scheduler;

	private static Logger logger = Log.getLogger();

	static {

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static void startJobWithSecond(Class<? extends Job> jobClass, int interval) {
		startJob(jobClass, interval,JobType.SECOND, null);
	}
	public static void startJobWithMinute(Class<? extends  Job> jobClass, int interval) {
		startJob(jobClass, interval,JobType.MINUTE, null);
	}
	public static void startJobWithinterval(Class<? extends Job> jobClass, int interval) {
		startJob(jobClass, interval,JobType.HOURS, null);
	}

	public static void startJob(Class<? extends Job> jobClass, int interval,int type,Object args) {

		JobDetail dataJob = JobBuilder.newJob(jobClass).build();

		if(args!=null ){
			dataJob.getJobDataMap().put("args", args);
		}
		
		SimpleScheduleBuilder schedule = SimpleScheduleBuilder.simpleSchedule();
		if(type==JobType.SECOND) {
			schedule.withIntervalInSeconds(interval);
		}else if(type==JobType.MINUTE) {
			schedule.withIntervalInMinutes(interval);
		}else if(type==JobType.HOURS){
			schedule.withIntervalInHours(interval);
		}

		SimpleTrigger dataTrigger = TriggerBuilder.newTrigger()
				.withSchedule(schedule.repeatForever())
				.forJob(dataJob).startNow().build();

		try {
			scheduler.scheduleJob(dataJob, dataTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

	}

}
