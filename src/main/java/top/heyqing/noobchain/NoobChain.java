package top.heyqing.noobchain;

import com.google.gson.GsonBuilder;
import top.heyqing.noobchain.domain.Block;

import java.util.ArrayList;

/**
 * ClassName:top.heyqing.noobchain.NoobChain
 * Package:top.heyqing.noobchain
 * Description:
 * 主类
 * from - https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
 *
 * @Date:2024/11/16
 * @Author:Heyqing
 */
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    public static void main(String[] args) {
        // part 1 start
//        testHash();
//        testGson();
//        System.out.println(isChainValid());
//        testMineBlock();
        // part 1 end
    }

    /*****************************************private-util-start*********************************************/
    private static void testMineBlock() {
        /*
        为每个新区块触发 mineBlock（） 方法。isChainValid（） 布尔值还应检查每个区块是否具有已解决的（通过挖矿）哈希值。
         */
        /*
        开采每个区块都需要一些时间！（ 约 3 秒 ）
        如果有人篡改😒了您的区块链系统中的数据
            他们的区块链将是无效的。
            他们将无法创建更长的区块链。
            您网络中诚实的区块链将在最长的链上具有时间优势。
         */
        blockchain.add(new Block("Hi im the first block", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }

    private static void testGson() {
        /*
        目前它不是一个太多的区块链，所以让我们将我们的区块存储在 ArrayList 中，并导入 gson 以 Json 格式查看它。
         */
        blockchain.add(new Block("Hi im the first block", "0"));
        blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));
        blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }

    private static void testHash() {
        /*
        第一个区块称为创世区块，因为没有前一个区块，所以我们只输入“0”作为前一个哈希值。
         */
        Block genesisBlock = new Block("Hi this is first block", "0");
        System.out.println("Hash for block 1:" + genesisBlock.hash);
        //Hash for block 1:601fa900c0f38b833a824506b8dab41db99d18305912fad172ddda2bc03e464a
        Block secondBlock = new Block("Hey im the second block", genesisBlock.hash);
        System.out.println("Hash for block 2:" + secondBlock.hash);
        //Hash for block 2:e7cc20e355cece65a4701e7ef48cd75cddbbf98e594e94d53fcd1bf660407c57
        Block thirdBlock = new Block("Yo third block is me", secondBlock.hash);
        System.out.println("Hash for block 3:" + thirdBlock.hash);
        //Hash for block 3:f06d63dfc61112c357b2e604df4a40428818c571382bf7f8f9bb76e5a46d2bbe
        /*
        现在，每个区块都有自己的数字签名，该签名基于其信息和前一个区块的签名。
         */
    }

    /*****************************************public-util-start*********************************************/

    /**
     * 检查区块链的完整性
     *
     * @return
     */
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

}
