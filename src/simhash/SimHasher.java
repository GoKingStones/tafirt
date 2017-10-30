package simhash;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.KeywordExtractor;



/**
 * 文本去重算法的simhash类
 * 步骤如下：
 * 1，对文本分词，得到N维特征向量（默认为64维）
 * 2，为分词设置权重（tf-idf）
 * 3，为特征向量计算哈希
 * 4，对所有特征向量加权，累加（目前仅进行非加权累加）
 * 5，对累加结果，大于零置一，小于零置零
 * 6，得到文本指纹（fingerprint）
 * 
 */
public class SimHasher {
	private String hash;
	private BigInteger signature;
	private KeywordExtractor wordExtractor = KeywordExtractor.getInstance();

	/**
	 * 构造函数
	 * 
	 * @param content 字符串
	 */
	public SimHasher(String content) {
		this.analysis(content);
	}

	private void analysis(String content) {
		Map<String, Double> wordInfos = wordExtractor.extract(content);
		double[] featureVector = new double[FNVHash.HASH_BITS];
		Set<String> words = wordInfos.keySet();
//		System.out.println(words);
		for (String word : words) {
			// 将每个特征feature转化为hash值
			BigInteger wordhash = FNVHash.fnv1aHash64(word);
			for (int i = 0; i < FNVHash.HASH_BITS; i++) {
				BigInteger bitmask = BigInteger.ONE.shiftLeft(FNVHash.HASH_BITS - i - 1);
                // 建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
                // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加weight,
                // 中间的62位减weight,也就是说,逢1加weight,逢0减weight.一直到把所有的分词hash数列全部判断完毕.
				// 这里的weight是计算的词的tf-idf值
				if (wordhash.and(bitmask).signum() != 0) {
					featureVector[i] += wordInfos.get(word);
				} else {
					featureVector[i] -= wordInfos.get(word);
				}
			}
		}

		BigInteger signature = BigInteger.ZERO;
		StringBuffer hashBuffer = new StringBuffer();
		for (int i = 0; i < FNVHash.HASH_BITS; i++) {
			 // 最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit的数字指纹
			if (featureVector[i] >= 0) {
				signature = signature.add(BigInteger.ONE.shiftLeft(FNVHash.HASH_BITS - i - 1));
				hashBuffer.append("1");
			} else {
				hashBuffer.append("0");
			}
		}
		this.hash = hashBuffer.toString();
		this.signature = signature;
	}

	/**
	 * 海明距离
	 * 取两个二进制的异或，统计为1的个数，就是海明距离
	 * @param targetSignature 比较签名
	 * @return
	 */
	public int getHammingDistance(BigInteger targetSignature) {
		BigInteger x = this.getSignature().xor(targetSignature);
		int tot = 0;

		// 统计x中二进制位数为1的个数
		// 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，
		// 对吧，然后，n&(n-1)就相当于把后面的数字清0，
		// 我们看n能做多少次这样的操作就OK了。

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}

		return tot;
	}

	/**
	 * hash距离。二进制比较
	 * 
	 * @param targetHash 比较目标
	 * @return
	 */
	public int getHashDistance(String targetHash) {
		int distance;
		if (this.getHash().length() != targetHash.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < this.getHash().length(); i++) {
				if (this.getHash().charAt(i) != targetHash.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}

	public String getHash() {
		return this.hash;
	}

	public BigInteger getSignature() {
		return this.signature;
	}

	/** 
     * 如果海明距离取3，则分成四块，并得到每一块的bigInteger值 ，作为索引值使用 
     * 计算 海明距离 在  distance 以内的各块签名的 hash 值
     * 根据鸽巢原理，如果两个签名的海明距离在distance以内，它们必有一块签名subByDistance()完全相同。
     * @param simHash 
     * @param distance 
     * @return 
     */
	public List<BigInteger> subByDistance(SimHasher simHash, int distance) {  
        // 分成几组来检查  
        int numEach = FNVHash.HASH_BITS / (distance + 1);  
        List<BigInteger> characters = new ArrayList<BigInteger>();  
  
        StringBuffer buffer = new StringBuffer();  
   
        String tmp = simHash.getSignature().toString(2);
        while(tmp.length() < FNVHash.HASH_BITS){
        	tmp = "0"+tmp;
        }
        String t = new StringBuilder(tmp).reverse().toString();
        for (int i = 0; i < t.length(); i++) {
        	if (t.charAt(i)=='1') {  
        		buffer.append("1");  
        	} 
        	else {  
        		buffer.append("0");  
        	} 
  
            if ((i + 1) % numEach == 0) {  
                // 将二进制转为BigInteger  
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);  
//                System.out.println("----" + eachValue);  
                buffer.delete(0, buffer.length());  
                characters.add(eachValue);  
            }  
        }  
//        System.out.println(characters);
        return characters;  
    }  
}
