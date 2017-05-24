package org.okraAx.login.bean;

/**
 * 游戏战报信息
 *
 * @author TinyZ.
 * @version 2017.05.18
 */
public class GameReport {

    private int uid;        //  角色ID
    private volatile int score;      //  总游戏积分
    private volatile int history;    //  历史最高游戏积分
    private volatile int totalCount; //  总游戏场次
    private volatile int winCount;   //  总胜利游戏场次
    private volatile int dropCount;  //  总掉线次数

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getDropCount() {
        return dropCount;
    }

    public void setDropCount(int dropCount) {
        this.dropCount = dropCount;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameReport that = (GameReport) o;

        if (uid != that.uid) return false;
        if (score != that.score) return false;
        if (history != that.history) return false;
        if (totalCount != that.totalCount) return false;
        if (winCount != that.winCount) return false;
        return dropCount == that.dropCount;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + score;
        result = 31 * result + history;
        result = 31 * result + totalCount;
        result = 31 * result + winCount;
        result = 31 * result + dropCount;
        return result;
    }

    @Override
    public String toString() {
        return "GameReport{" +
                "uid=" + uid +
                ", score=" + score +
                ", history=" + history +
                ", totalCount=" + totalCount +
                ", winCount=" + winCount +
                ", dropCount=" + dropCount +
                '}';
    }
}
