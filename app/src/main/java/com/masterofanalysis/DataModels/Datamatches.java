package com.masterofanalysis.DataModels;

import java.util.List;

public class Datamatches {
    private String status;
    private String message;
    private List<Matches> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Matches> getData() {
        return data;
    }

    public void setData(List<Matches> data) {
        this.data = data;
    }

    public Datamatches() {

    }

    public class Matches {
        private String tarih;
        private String saat;
        private String evsahibi;
        private String deplasman;
        private String tahmin;
        private String tahmining;
        private String oran;
        private String league_name;
        private String macsonucu;
        private String durum;
        private String home_logo;

        public String getHome_logo() {
            return home_logo;
        }

        private String away_logo;

        public String getTarih() {
            return tarih;
        }

        public void setTarih(String tarih) {
            this.tarih = tarih;
        }

        public String getSaat() {
            return saat;
        }

        public void setSaat(String saat) {
            this.saat = saat;
        }

        public String getEvsahibi() {
            return evsahibi;
        }

        public void setEvsahibi(String evsahibi) {
            this.evsahibi = evsahibi;
        }

        public String getDeplasman() {
            return deplasman;
        }

        public void setDeplasman(String deplasman) {
            this.deplasman = deplasman;
        }

        public String getTahmin() {
            return tahmin;
        }

        public void setTahmin(String tahmin) {
            this.tahmin = tahmin;
        }

        public String getTahmining() {
            return tahmining;
        }

        public void setTahmining(String tahmining) {
            this.tahmining = tahmining;
        }

        public String getOran() {
            return oran;
        }

        public void setOran(String oran) {
            this.oran = oran;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }

        public String getMacsonucu() {
            return macsonucu;
        }

        public void setMacsonucu(String macsonucu) {
            this.macsonucu = macsonucu;
        }

        public String getDurum() {
            return durum;
        }

        public void setDurum(String durum) {
            this.durum = durum;
        }

        public String getHomeLogoUrl() {
            return home_logo;
        }


        public String getAwayLogoUrl() {
            return away_logo;
        }

        public void setAwayLogoUrl(String awayLogoUrl) {
            this.away_logo = awayLogoUrl;
        }


    }
}
