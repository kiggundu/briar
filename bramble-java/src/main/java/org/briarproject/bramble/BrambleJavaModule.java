package org.briarproject.bramble;

import org.briarproject.bramble.io.DnsModule;
import org.briarproject.bramble.mailbox.ModularMailboxModule;
import org.briarproject.bramble.network.JavaNetworkModule;
import org.briarproject.bramble.socks.SocksModule;
import org.briarproject.bramble.system.JavaSystemModule;
import org.briarproject.onionwrapper.CircumventionModule;

import dagger.Module;

@Module(includes = {
		CircumventionModule.class,
		DnsModule.class,
		JavaNetworkModule.class,
		JavaSystemModule.class,
		ModularMailboxModule.class,
		SocksModule.class
})
public class BrambleJavaModule {

}
