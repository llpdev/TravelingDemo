package com.ptu.mata.bean;

import java.util.List;

/**
 * Created by Mr-Hei on 2016/10/27.
 */

public class TestBean {


    /**
     * status : true
     * total : 585
     * tngou : [{"count":15693,"description":"7）加盖，置于蒸锅内，隔水以文火炖2个小时，以表面呈现少量泡沫，有点沸腾、粘稠感和蛋清香味为蒸好的标准","fcount":0,"food":"干血燕燕窝,冰糖","id":183,"images":"","img":"/cook/150802/1340f07baad474a757825191701d5e1e.jpg","keywords":"燕窝 纯净水 膨胀 冰糖 清洗 ","name":"冰糖燕窝","rcount":0}]
     */

    private boolean status;
    private int total;
    /**
     * count : 15693
     * description : 7）加盖，置于蒸锅内，隔水以文火炖2个小时，以表面呈现少量泡沫，有点沸腾、粘稠感和蛋清香味为蒸好的标准
     * fcount : 0
     * food : 干血燕燕窝,冰糖
     * id : 183
     * images :
     * img : /cook/150802/1340f07baad474a757825191701d5e1e.jpg
     * keywords : 燕窝 纯净水 膨胀 冰糖 清洗
     * name : 冰糖燕窝
     * rcount : 0
     */

    private List<TngouBean> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TngouBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<TngouBean> tngou) {
        this.tngou = tngou;
    }

    public static class TngouBean {
        private int count;
        private String description;
        private int fcount;
        private String food;
        private int id;
        private String images;
        private String img;
        private String keywords;
        private String name;
        private int rcount;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }
    }
}
