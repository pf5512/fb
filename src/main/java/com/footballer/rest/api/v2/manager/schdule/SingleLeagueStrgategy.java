package com.footballer.rest.api.v2.manager.schdule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleLeagueStrgategy implements ScheduleStrategy {

	@Override
	public List<ScheduleMatchItem> generate(List<Long> teamIdList) {
		return begerArrangement(teamIdList);
	}

	public static void main(String[] args) {
		Long[] array = new Long[] { 7L, 8L, 9L, 10L, 11L };
		begerArrangement(Arrays.asList(array));
	}

	private static List<ScheduleMatchItem> begerArrangement(List<Long> teamIdList) {
		
		List<ScheduleMatchItem> scheduleMatchList = new ArrayList<ScheduleMatchItem>();

		int nAmount = teamIdList.size();

		if (nAmount < 2 || nAmount > 90)
			return scheduleMatchList;

		// 队伍数量
		int nFixAmount = nAmount;
		// 最后一支队伍的编号
		int nLastPlayerNo = nAmount;
		// 奇数队伍，补上一支虚拟的队伍，最后一支队伍的编号为0

		if ((nAmount % 2) != 0) {
			++nFixAmount;
			nLastPlayerNo = 0;
		}
		// 轮数
		int nMaxRound = nFixAmount - 1;
		int nHalfAmount = nFixAmount / 2;

		// 移动的间隔
		int nStep = nFixAmount <= 4 ? 1 : (nFixAmount - 4) / 2 + 1;

		int nRound = 1;
		int nFirstPlayerPos = 1;
		int nLastPlayerPos = 1;

		int result[][] = new int[100][200];

		while (nRound <= nMaxRound) {
			// 每次最后一个玩家的位置需要左右对调
			nLastPlayerPos = nFixAmount + 1 - nLastPlayerPos;

			if (nRound == 1)
				nFirstPlayerPos = 1;
			else
				nFirstPlayerPos = (nFirstPlayerPos + nStep) % (nFixAmount - 1);

			if (nFirstPlayerPos == 0)
				nFirstPlayerPos = nFixAmount - 1;

			if (nFirstPlayerPos == nLastPlayerPos)
				nFirstPlayerPos = nFixAmount + 1 - nLastPlayerPos;

			for (int i = 1; i <= nHalfAmount; ++i) {
				int nPos[] = { i, nFixAmount - i + 1 };
				int nPlayer[] = { 0, 0 };
				for (int j = 0; j < 2; ++j) {
					if (nPos[j] == nLastPlayerPos)
						nPlayer[j] = nLastPlayerNo;
					else if (nPos[j] < nFirstPlayerPos)
						nPlayer[j] = nFixAmount - nFirstPlayerPos + nPos[j];
					else
						nPlayer[j] = nPos[j] - nFirstPlayerPos + 1;

					result[i - 1][(nRound - 1) * 2 + j] = nPlayer[j];
				}
			}

			++nRound;
		}

		/*
		 * for (int i = 1; i <= nMaxRound; ++i) { if( i == 1 )
		 * System.out.println("r: " + i); else System.out.println("r" + i); }
		 * 
		 * System.out.println("\n");
		 */

		String[] resultAgenda = new String[nMaxRound];

		for (int j = 0; j < nMaxRound; ++j) {
			String tresult = "";
			for (int i = 0; i < nHalfAmount; ++i) {
				if (tresult == "") {
					tresult = result[i][j * 2] + "-" + result[i][j * 2 + 1];
				} else {
					tresult = tresult + "," + result[i][j * 2] + "-"
							+ result[i][j * 2 + 1];
				}

				int hostIndex = result[i][j * 2];
				int guestIndex = result[i][j * 2 + 1];

				Long hostTeamId = 0 == hostIndex ? -1L : teamIdList
						.get(hostIndex - 1);
				Long guestTeamId = 0 == guestIndex ? -1L : teamIdList
						.get(guestIndex - 1);

				// System.out.println( result[i][j*2] + "-" + result[i][j*2+1]);

				// System.out.println("=====");

				System.out.println(hostTeamId + " : " + guestTeamId + ", round:" + j);
				scheduleMatchList.add(new ScheduleMatchItem(hostTeamId, guestTeamId, j));
			}
			resultAgenda[j] = tresult;
			System.out.println("----------------------------");
		}
		System.out.println("\n");

		/*
		 * for (int j = 0; j < nMaxRound; ++j) {
		 * System.out.println(resultAgenda[j]); }
		 */

		System.out.println("\n\n");
		
		return scheduleMatchList;
	}

}
