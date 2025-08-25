package com.vishwakarma.vastu.utils;

import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vishwakarma.vastu.model.User;

/**
 * @author Gunnar Hillert
 * @since  1.0
 *
 */
public final class AtmosphereUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AtmosphereUtils.class);

	public static AtmosphereResource getAtmosphereResource(HttpServletRequest request) {
		return getMeteor(request).getAtmosphereResource();
	}
	
	public static Meteor getMeteor(HttpServletRequest request) {
		return Meteor.build(request);
	}

	public static void suspend(final AtmosphereResource resource, User user) {

		final CountDownLatch countDownLatch = new CountDownLatch(1);
		resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
			@Override
			public void onSuspend(AtmosphereResourceEvent event) {
				countDownLatch.countDown();
				LOG.info("Suspending Client..." + resource.uuid());
				resource.removeEventListener(this);
			}

			@Override
			public void onDisconnect(AtmosphereResourceEvent event) {
				LOG.info("Disconnecting Client..." + resource.uuid());
				super.onDisconnect(event);
			}

			@Override
			public void onBroadcast(AtmosphereResourceEvent event) {
				LOG.info("Client is broadcasting..." + resource.uuid());
				super.onBroadcast(event);
			}

		});

		Broadcaster selfBroadcaster = AtmosphereUtils.lookupBroadcaster().addAtmosphereResource(resource);
		selfBroadcaster.addAtmosphereResource(resource);
		BroadcasterFactory.getDefault().add(selfBroadcaster, user.getEmail());

		if (AtmosphereResource.TRANSPORT.LONG_POLLING.equals(resource.transport())) {
			resource.resumeOnBroadcast(true).suspend(-1, false);
		} else {
			resource.suspend(-1);
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOG.error("Interrupted while trying to suspend resource {}", resource);
		}
	}

	public static Broadcaster lookupBroadcaster() {
		Broadcaster b = BroadcasterFactory.getDefault().get();
		return b;
	}

}
