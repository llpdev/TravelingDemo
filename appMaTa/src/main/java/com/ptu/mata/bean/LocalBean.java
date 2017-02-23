package com.ptu.mata.bean;

import java.util.List;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class LocalBean {

    /**
     * Err : 0
     * Msg :
     * Items : [{"Id":11,"Name":"西塘","Image":"http://img2.miaotu.net/2015-12-31/abce1e20582828993149390992f2128e.jpg","Content":"","Status":1,"YueyouCount":742,"ComResCount":23,"ComRes":null,"Sort":49},{"Id":18,"Name":"杭州","Image":"http://img2.miaotu.net/2015-12-31/fa25bf50053ebb4a381813a0139c792e.jpg","Content":"","Status":1,"YueyouCount":1731,"ComResCount":32,"ComRes":null,"Sort":48},{"Id":24,"Name":"三亚","Image":"http://img2.miaotu.net/2015-12-31/ec646cd72fe46348b7a058b43c1bcb51.jpg","Content":"","Status":1,"YueyouCount":2637,"ComResCount":59,"ComRes":null,"Sort":47},{"Id":9,"Name":"苏州","Image":"http://img2.miaotu.net/2015-12-31/be228a0fe055f56c87204d0e114535f5.jpg","Content":"","Status":1,"YueyouCount":749,"ComResCount":43,"ComRes":null,"Sort":46},{"Id":15,"Name":"厦门","Image":"http://img2.miaotu.net/2015-12-31/6743490f2c61d27bb34aa807db3e5a4d.jpg","Content":"","Status":1,"YueyouCount":2367,"ComResCount":90,"ComRes":null,"Sort":45},{"Id":17,"Name":"南京","Image":"http://img2.miaotu.net/2015-12-31/46825fc43242d7345780f5a36e505b6a.jpg","Content":"","Status":1,"YueyouCount":681,"ComResCount":7,"ComRes":null,"Sort":20},{"Id":25,"Name":"西安","Image":"http://img2.miaotu.net/2015-12-31/4952a8d2dc75e110cd3c70cdfa6ef6e3.jpg","Content":"","Status":1,"YueyouCount":1648,"ComResCount":11,"ComRes":null,"Sort":19},{"Id":6,"Name":"武汉","Image":"http://img2.miaotu.net/2015-12-31/69512be9a26810c5fb8c057eb849cdd2.jpg","Content":"","Status":1,"YueyouCount":540,"ComResCount":19,"ComRes":null,"Sort":18},{"Id":20,"Name":"重庆","Image":"http://img2.miaotu.net/2015-12-31/362cd01301cc64b59540e14bb43ada19.jpg","Content":"","Status":1,"YueyouCount":1343,"ComResCount":12,"ComRes":null,"Sort":14},{"Id":19,"Name":"成都","Image":"http://img2.miaotu.net/2015-12-31/f6adccef8ed5ed276cabe65e8c9c9966.jpg","Content":"","Status":1,"YueyouCount":3735,"ComResCount":15,"ComRes":null,"Sort":13}]
     * ServerTime : 2016-10-28 17:33:27
     */

    private int Err;
    private String Msg;
    private String ServerTime;
    /**
     * Id : 11
     * Name : 西塘
     * Image : http://img2.miaotu.net/2015-12-31/abce1e20582828993149390992f2128e.jpg
     * Content :
     * Status : 1
     * YueyouCount : 742
     * ComResCount : 23
     * ComRes : null
     * Sort : 49
     */

    private List<ItemsBean> Items;

    public int getErr() {
        return Err;
    }

    public void setErr(int Err) {
        this.Err = Err;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String ServerTime) {
        this.ServerTime = ServerTime;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class ItemsBean {
        private int Id;
        private String Name;
        private String Image;
        private String Content;
        private int Status;
        private int YueyouCount;
        private int ComResCount;
        private Object ComRes;
        private int Sort;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getYueyouCount() {
            return YueyouCount;
        }

        public void setYueyouCount(int YueyouCount) {
            this.YueyouCount = YueyouCount;
        }

        public int getComResCount() {
            return ComResCount;
        }

        public void setComResCount(int ComResCount) {
            this.ComResCount = ComResCount;
        }

        public Object getComRes() {
            return ComRes;
        }

        public void setComRes(Object ComRes) {
            this.ComRes = ComRes;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }
    }
}
