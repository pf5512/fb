package com.footballer.rest.api.v1.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ian on 8/4/14.
 */
public class SearchTeamResponse extends JsonResponse{

    private List<SearchTeam> teams;

    public SearchTeamResponse(String status, String message) {
        super(status, message);
    }

    public List<SearchTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<SearchTeam> teams) {
        this.teams = teams;
    }

    public static class SearchTeam {

        private String name;
        private String isStrong;
        private BigDecimal avgTechnical;
        private BigDecimal avgReputation;
        private BigDecimal avgAttitude;

        public SearchTeam() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsStrong() {
            return isStrong;
        }

        public void setIsStrong(String isStrong) {
            this.isStrong = isStrong;
        }

        public BigDecimal getAvgTechnical() {
            return avgTechnical;
        }

        public void setAvgTechnical(BigDecimal avgTechnical) {
            this.avgTechnical = avgTechnical;
        }

        public BigDecimal getAvgReputation() {
            return avgReputation;
        }

        public void setAvgReputation(BigDecimal avgReputation) {
            this.avgReputation = avgReputation;
        }

        public BigDecimal getAvgAttitude() {
            return avgAttitude;
        }

        public void setAvgAttitude(BigDecimal avgAttitude) {
            this.avgAttitude = avgAttitude;
        }
    }


}
