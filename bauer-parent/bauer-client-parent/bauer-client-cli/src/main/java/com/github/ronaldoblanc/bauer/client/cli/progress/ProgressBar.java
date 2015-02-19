package com.github.ronaldoblanc.bauer.client.cli.progress;

/**
 * Command line progress bar.
 * 
 * @author Ronaldo Blanc <ronaldoblanc at gmail.com>
 */
public class ProgressBar {
	private StringBuilder progress;

	public ProgressBar() {
		init();
	}

	/**
	 * Updates progress bar state.
	 * 
	 * @param done
	 * @param total
	 */
	public void update(int done, int total) {
		char[] workchars = { '|', '/', '-', '\\' };
		String format = "\r%3d%% %s %c";

		int percent = (++done * 100) / total;
		int extrachars = (percent / 2) - this.progress.length();

		while (extrachars-- > 0) {
			progress.append('#');
		}

		synchronized (System.out) {
			System.out.printf(format, percent, progress, workchars[done
					% workchars.length]);

			if (done == total) {
				System.out.flush();
				System.out.println();
				init();
			}
		}
	}

	/**
	 * Updates progress bar state.
	 * 
	 * @param done
	 * @param total
	 */
	public void update(long done, long total) {
		update(new Long(done).intValue(), new Long(total).intValue());
	}

	/**
	 * Resets the progress bar state.
	 */
	public void reset() {
		init();
	}

	private void init() {
		this.progress = new StringBuilder(60);
	}
}
