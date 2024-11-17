package top.heyqing.noobchain.domain;

import top.heyqing.noobchain.utils.StringUtil;

import java.util.Date;

/**
 * ClassName:java.noobchain.domain.Block
 * Package:top.heyqing.noobchain.domain
 * Description:
 * 区块
 *
 * @Date:2024/11/16
 * @Author:Heyqing
 */
public class Block {

    /**
     * hash值
     */
    public String hash;
    /**
     * 前一个hash值
     */
    public String previousHash;
    /**
     * 区块链数据
     */
    private String data;
    /**
     * 时间戳
     */
    private long timeStamp;
    /**
     *
     */
    private int nonce;

    public Block(String previousHash, String data) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = new Date().getTime();
        // 确保我们在设置其他值后执行此操作。
        this.hash = calculateHash();
    }

    /**
     * 计算hash值
     *
     * @return
     */
    public String calculateHash() {
        return StringUtil.applySha256(previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) +
                data);
    }

    /**
     * 挖掘
     * <p>
     * difficulty 的 int，这是他们必须解决的 0 的数量
     *
     * @param difficulty
     */
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
