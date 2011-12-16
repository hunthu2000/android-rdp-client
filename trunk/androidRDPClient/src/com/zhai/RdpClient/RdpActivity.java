package com.zhai.RdpClient;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.propero.rdp.Common;
import net.propero.rdp.ConnectionException;
import net.propero.rdp.Constants;
import net.propero.rdp.Options;
import net.propero.rdp.RDPClientChooser;
import net.propero.rdp.RdesktopException;
import net.propero.rdp.Rdp;
import net.propero.rdp.rdp5.Rdp5;
import net.propero.rdp.rdp5.VChannels;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class RdpActivity extends Activity {

	private static final String TAG = "AndroidRDPClientActivity";

	static boolean keep_running;

	static boolean loggedon;

	static boolean readytosend;

	static boolean showTools;

	static String mapFile = "en-gb";

	static String keyMapLocation = "";

	static String server = "10.2.60.23";
	static String password="312";
	int logonflags = Rdp.RDP_LOGON_NORMAL;
	
	// static SendEvent toolFrame = null;
	
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		Intent intent = new Intent();
//		intent.setClass(AndroidRDPClientActivity.this, CanvasActivity.class);
//		startActivity(intent);
		 Rdesktop();

	}

	private void Rdesktop() {
		// 初始化和显示桌面
		init();

	}

	private void init() {
		keep_running = true;
		loggedon = false; // 未登陆
		readytosend = false;
		showTools = false;
		mapFile = "en-gb";
		keyMapLocation = "";
		// toolFrame = null;

		BasicConfigurator.configure();

		RDPClientChooser Chooser = new RDPClientChooser();
		String[] args = { "10.2.60.23" };
		if (Chooser.RunNativeRDPClient(args)) { // Chooser.RunNativeRDPClient(args)
			// = false
			if (!Common.underApplet)
				System.exit(0);
		}
		int logonflags = Rdp.RDP_LOGON_NORMAL;
		// 正常登陆 自动登陆

		boolean fKdeHack = false;
		int c;
		String arg;
		StringBuffer sb = new StringBuffer();
		LongOpt[] alo = new LongOpt[15];
		alo[0] = new LongOpt("debug_key", LongOpt.NO_ARGUMENT, null, 0);
		alo[1] = new LongOpt("debug_hex", LongOpt.NO_ARGUMENT, null, 0);
		alo[2] = new LongOpt("no_paste_hack", LongOpt.NO_ARGUMENT, null, 0);
		alo[3] = new LongOpt("log4j_config", LongOpt.REQUIRED_ARGUMENT, sb, 0);
		alo[4] = new LongOpt("packet_tools", LongOpt.NO_ARGUMENT, null, 0);
		alo[5] = new LongOpt("quiet_alt", LongOpt.NO_ARGUMENT, sb, 0);
		alo[6] = new LongOpt("no_remap_hash", LongOpt.NO_ARGUMENT, null, 0);
		alo[7] = new LongOpt("no_encryption", LongOpt.NO_ARGUMENT, null, 0);
		alo[8] = new LongOpt("use_rdp4", LongOpt.NO_ARGUMENT, null, 0);
		alo[9] = new LongOpt("use_ssl", LongOpt.NO_ARGUMENT, null, 0);
		alo[10] = new LongOpt("enable_menu", LongOpt.NO_ARGUMENT, null, 0);
		alo[11] = new LongOpt("console", LongOpt.NO_ARGUMENT, null, 0);
		alo[12] = new LongOpt("load_licence", LongOpt.NO_ARGUMENT, null, 0);
		alo[13] = new LongOpt("save_licence", LongOpt.NO_ARGUMENT, null, 0);
		alo[14] = new LongOpt("persistent_caching", LongOpt.NO_ARGUMENT, null,
				0);

		String progname = "properJavaRDP";// 程序名

		Getopt g = new Getopt("properJavaRDP", args,
				"bc:d:f::g:k:l:m:n:p:s:t:T:u:o:r:", alo);

		// ClipChannel clipChannel = new ClipChannel(); //省略

		while ((c = g.getopt()) != -1) {
			switch (c) {

			case 0:
				switch (g.getLongind()) {
				case 0:
					Options.debug_keyboard = true;
					break;
				case 1:
					Options.debug_hexdump = true;
					break;
				case 2:
					break;
				case 3:
					arg = g.getOptarg();
					PropertyConfigurator.configure(arg);
					// logger.info("Log4j using config file " + arg);
					break;
				case 4:
					showTools = true;
					break;
				case 5:
					Options.altkey_quiet = true;
					break;
				case 6:
					Options.remap_hash = false;
					break;
				case 7:
					Options.packet_encryption = false;
					break;
				case 8:
					Options.use_rdp5 = false;
					// Options.server_bpp = 8;
					Options.set_bpp(8);
					break;
				case 9:
					Options.use_ssl = true;
					break;
				case 10:
					Options.enable_menu = true;
					break;
				case 11:
					Options.console_session = true;
					break;
				case 12:
					Options.load_licence = true;
					break;
				case 13:
					Options.save_licence = true;
					break;
				case 14:
					Options.persistent_bitmap_caching = true;
					break;
				default:
					// usage();
				}
				break;

			case 'o':
				Options.set_bpp(Integer.parseInt(g.getOptarg()));
				break;
			case 'b':
				Options.low_latency = false;
				break;
			case 'm':
				mapFile = g.getOptarg();
				break;
			case 'c':
				Options.directory = g.getOptarg();
				break;
			case 'd':
				Options.domain = g.getOptarg();
				break;
			case 'f':
				// Dimension screen_size = Toolkit.getDefaultToolkit()
				// .getScreenSize();

				// Options.width = screen_size.width & ~3;
				// Options.height = screen_size.height;

				Options.width = 600 & ~3;
				Options.height = 800;
				Options.fullscreen = true;
				arg = g.getOptarg();
				if (arg != null) {
					if (arg.charAt(0) == 'l')
						fKdeHack = true;
					else {
						System.err.println(progname
								+ ": Invalid fullscreen option '" + arg + "'");
						// usage();
					}
				}
				break;
			case 'g':
				arg = g.getOptarg();
				int cut = arg.indexOf("x", 0);
				if (cut == -1) {
					System.err.println(progname + ": Invalid geometry: " + arg);
					// usage();
				}
				Options.width = Integer.parseInt(arg.substring(0, cut)) & ~3;
				Options.height = Integer.parseInt(arg.substring(cut + 1));
				break;
			case 'k':
				arg = g.getOptarg();
				// Options.keylayout = KeyLayout.strToCode(arg);
				if (Options.keylayout == -1) {
					System.err.println(progname + ": Invalid key layout: "
							+ arg);
					// usage();
				}
				break;
			case 'l':
				arg = g.getOptarg();
				switch (arg.charAt(0)) {
				case 'd':
				case 'D':
					// logger.setLevel(Level.DEBUG);
					break;
				case 'i':
				case 'I':
					// logger.setLevel(Level.INFO);
					break;
				case 'w':
				case 'W':
					// logger.setLevel(Level.WARN);
					break;
				case 'e':
				case 'E':
					// logger.setLevel(Level.ERROR);
					break;
				case 'f':
				case 'F':
					// logger.setLevel(Level.FATAL);
					break;
				default:
					System.err.println(progname + ": Invalid debug level: "
							+ arg.charAt(0));
					// usage();
				}
				break;
			case 'n':
				Options.hostname = g.getOptarg();
				break;
			case 'p':
				Options.password = g.getOptarg();
				logonflags |= Rdp.RDP_LOGON_AUTO;
				break;
			case 's':
				Options.command = g.getOptarg();
				break;
			case 'u':
				Options.username = g.getOptarg();
				break;
			case 't':
				arg = g.getOptarg();
				try {
					Options.port = Integer.parseInt(arg);
				} catch (NumberFormatException nex) {
					System.err.println(progname + ": Invalid port number: "
							+ arg);
					// usage();
				}
				break;
			case 'T':
				Options.windowTitle = g.getOptarg().replace('_', ' ');
				break;
			case 'r':
				Options.licence_path = g.getOptarg();
				break;

			case '?':
			default:
				// usage();
				break;

			}
		}

		if (fKdeHack) {
			Options.height -= 46;
		}

		String server = null;
		logonflags |= Rdp.RDP_LOGON_AUTO;   // 自动登陆的话， 需要填上用户名和密码
		if (g.getOptind() < args.length) {
			int colonat = args[args.length - 1].indexOf(":", 0);
			if (colonat == -1) {
				server = args[args.length - 1];
			} else {
				server = args[args.length - 1].substring(0, colonat);
				Options.port = Integer.parseInt(args[args.length - 1]
						.substring(colonat + 1));
			}
		} else {
			System.err.println(progname + ": A server name is required!");
			// usage();
		}

		VChannels channels = new VChannels();
		// Initialise all RDP5 channels
		if (Options.use_rdp5) {
			// TODO: implement all relevant channels
			// if (Options.map_clipboard)

			// channels.register(clipChannel);
		}
		String os = System.getProperty("os.name");
		String osvers = System.getProperty("os.version");
		if (os.equals("Windows 2000") || os.equals("Windows XP"))
			Options.built_in_licence = true;

		Log.i(TAG, "Operating System is " + os + " version " + osvers);

		if (os.startsWith("Linux"))
			Constants.OS = Constants.LINUX;
		else if (os.startsWith("Windows"))
			Constants.OS = Constants.WINDOWS;
		else if (os.startsWith("Mac"))
			Constants.OS = Constants.MAC;

		if (Constants.OS == Constants.MAC)
			Options.caps_sends_up_and_down = false;

		Rdp5 RdpLayer = null;
		Common.rdp = RdpLayer;
		// RdesktopFrame window = new RdesktopFrame_Localised();

		boolean[] deactivated = new boolean[1];
		int[] ext_disc_reason = new int[1];

		while (keep_running) {
			// logger.debug("Initialising RDP layer...");
			RdpLayer = new Rdp5(channels);
			Common.rdp = RdpLayer;
			// logger.debug("Registering drawing surface...");
			// RdpLayer.registerDrawingSurface(window);
			// logger.debug("Registering comms layer...");
			// window.registerCommLayer(RdpLayer);
			loggedon = false;
			readytosend = false;

			if (server.equalsIgnoreCase("localhost"))
				server = "127.0.0.1";

			if (RdpLayer != null) {
				// Attempt to connect to server on port Options.port
				try {
					RdpLayer.connect(Options.username, InetAddress
							.getByName(server), logonflags, Options.domain,
							password, Options.command, Options.directory);

					// Remove to get rid of sendEvent tool
					if (showTools) {
						// toolFrame = new SendEvent(RdpLayer);
						// toolFrame.show();
					}
					// End

					if (keep_running) {

						/*
						 * By setting encryption to False here, we have an
						 * encrypted login packet but unencrypted transfer of
						 * other packets
						 */
						if (!Options.packet_encryption)
							Options.encryption = false;

						// logger.info("Connection successful");
						// now show window after licence negotiation
						RdpLayer.mainLoop(deactivated, ext_disc_reason);
						if (deactivated[0]) {
							/* clean disconnect */
							System.exit(-1);

							// return 0;
						} else {
							System.exit(-1);

						}

						keep_running = false; // exited main loop
						if (!readytosend) {
							// maybe the licence server was having a comms
							// problem, retry?
							String msg1 = "The terminal server disconnected before licence negotiation completed.";
							String msg2 = "Possible cause: terminal server could not issue a licence.";
							String[] msg = { msg1, msg2 };
							// logger.warn(msg1);
							// logger.warn(msg2);
							// window.showErrorDialog(msg);
						}
					} // closing bracket to if(running)

					// Remove to get rid of tool window
					// if (showTools)
					// toolFrame.dispose();
					// End

				} catch (ConnectionException e) {
					String msg[] = { "Connection Exception", e.getMessage() };
					// window.showErrorDialog(msg);
					//System.exit(-1);
					this.finish();
				} catch (UnknownHostException e) {
					// error(e, RdpLayer, window, true);
				} catch (SocketException s) {
					if (RdpLayer.isConnected()) {
						// logger.fatal(s.getClass().getName() + " "
						// + s.getMessage());
						// // s.printStackTrace();
						// error(s, RdpLayer, window, true);
						// Rdesktop.exit(0, RdpLayer, window, true);
					}
				} catch (RdesktopException e) {
					String msg1 = e.getClass().getName();
					String msg2 = e.getMessage();
					// logger.fatal(msg1 + ": " + msg2);

					e.printStackTrace(System.err);

					if (!readytosend) {
						// maybe the licence server was having a comms
						// problem, retry?
						String msg[] = {
								"The terminal server reset connection before licence negotiation completed.",
								"Possible cause: terminal server could not connect to licence server.",
								"Retry?" };
						// boolean retry = window.showYesNoErrorDialog(msg);
						// if (!retry) {
						// logger.info("Selected not to retry.");
						// Rdesktop.exit(0, RdpLayer, window, true);
						// } else {
						// if (RdpLayer != null && RdpLayer.isConnected()) {
						// logger.info("Disconnecting ...");
						// RdpLayer.disconnect();
						// logger.info("Disconnected");
						// }
						// logger.info("Retrying connection...");
						// keep_running = true; // retry
						// continue;
						// }
					} else {
						String msg[] = { e.getMessage() };
						// window.showErrorDialog(msg);
						// Rdesktop.exit(0, RdpLayer, window, true);
					}
				} catch (Exception e) {
					// logger.warn(e.getClass().getName() + " " +
					// e.getMessage());
					e.printStackTrace();
					// error(e, RdpLayer, window, true);
				}
			} else { // closing bracket to if(!rdp==null)
				// logger
				// .fatal("The communications layer could not be initiated!");
			}
		}
		System.exit(-1);
	}
}