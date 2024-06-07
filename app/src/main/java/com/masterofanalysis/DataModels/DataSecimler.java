package com.masterofanalysis.DataModels;

import java.util.List;

public class DataSecimler {

    private String status;
    private String message;
    private List<icerik> data;

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

    public List<icerik> getData() {
        return data;
    }

    public void setData(List<icerik> data) {
        this.data = data;
    }

    public class icerik {
        private String kupon_id,tarih,saat,toplam_oran,kredi,durum,aciklama,aciklamaing,oynayan_sayisi;

        public String getKupon_id() {
            return kupon_id;
        }

        public void setKupon_id(String kupon_id) {
            this.kupon_id = kupon_id;
        }

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

        public String getToplam_oran() {
            return toplam_oran;
        }

        public void setToplam_oran(String toplam_oran) {
            this.toplam_oran = toplam_oran;
        }

        public String getKredi() {
            return kredi;
        }

        public void setKredi(String kredi) {
            this.kredi = kredi;
        }

        public String getDurum() {
            return durum;
        }

        public void setDurum(String durum) {
            this.durum = durum;
        }

        public String getAciklama() {
            return aciklama;
        }

        public void setAciklama(String aciklama) {
            this.aciklama = aciklama;
        }

        public String getAciklamaing() {
            return aciklamaing;
        }

        public void setAciklamaing(String aciklamaing) {
            this.aciklamaing = aciklamaing;
        }

        public String getOynayan_sayisi() {
            return oynayan_sayisi;
        }

        public void setOynayan_sayisi(String oynayan_sayisi) {
            this.oynayan_sayisi = oynayan_sayisi;
        }
    }
}
