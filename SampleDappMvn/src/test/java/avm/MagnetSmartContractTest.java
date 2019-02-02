package avm;

import org.aion.avm.api.ABIEncoder;
import org.aion.avm.core.util.AvmRule;
import org.aion.avm.core.util.HashUtils;
import org.aion.avm.userlib.AionList;
import org.aion.avm.userlib.AionMap;
import org.aion.vm.api.interfaces.Address;
import org.aion.vm.api.interfaces.ResultCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.math.BigInteger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class MagnetSmartContractTest {

    //@ClassRule will instantiate vm and kernel once for whole class. use @rule to instantiate for each test case.
    //Set debugMode to true to enable debugging in IDE
    @ClassRule
    public static AvmRule avmRule = new AvmRule(true);

    //address with balance
    private Address from = avmRule.getPreminedAccount();

    private Address dappAddr;

    @Before
    public void deployDapp() {
        //deploy Dapp:
        // 1- get the Dapp byes to be used for the deploy transaction
        // 2- deploy the Dapp and get the address.
//        byte[] dapp = avmRule.getDappBytes(MagnetSmartContract.class, null, AionMap.class);
        byte[] dapp = avmRule.getDappBytes(MagnetSmartContract.class, null, AionMap.class, AionList.class, DiffUtils.class);
        AvmRule.ResultWrapper resultWrapper = avmRule.deploy(from, BigInteger.ZERO, dapp);
        dappAddr = resultWrapper.getDappAddress();
    }

    /*@Test
    public void testSumInput() {
        //calling Dapps:
        // 1- encode method name and arguments
        // 2- make the call
        long energyLimit = 2_000_000L;
        byte[] txData = ABIEncoder.encodeMethodArguments("sum", 15, 10);
        AvmRule.ResultWrapper result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData, energyLimit, 1L);

        // getReceiptStatus() checks the status of the transaction execution
        ResultCode status = result.getReceiptStatus();
        Assert.assertTrue(status.isSuccess());

        // getDecodedReturnData() reads the data sent from the Dapp
        Object sum = result.getDecodedReturnData();
        assertEquals(15 + 10, sum);

        // result.getLogs() return the generated events
        assertEquals(0, result.getLogs().size());

        // result.getTransactionResult() can be used for additional information
        Assert.assertTrue(result.getTransactionResult().getEnergyRemaining() < energyLimit);
        long energyUsed = energyLimit - result.getTransactionResult().getEnergyRemaining();
        Assert.assertTrue(energyUsed > 21_000L);
    }*/

    @Test
    public void testUpload() {
        // interaction with map
        //create a new account with initial balance to send the transaction
        Address sender = avmRule.getRandomAddress(BigInteger.valueOf(10_000_000L));

        byte[] txData = ABIEncoder.encodeMethodArguments("upload", "nero", "magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        ResultCode status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("upload", "micro", "magnet:?xt=urn:btih:4d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("upload", "micro", "magnet:?xt=urn:btih:4d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("getMagnetLink", 0);
        AvmRule.ResultWrapper result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);
        System.out.println("Data received "+result.getDecodedReturnData());

        txData = ABIEncoder.encodeMethodArguments("getMagnetLink", 1);
        result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);
        System.out.println("Data received "+result.getDecodedReturnData());

//        Not working for List to Array conversion in SC
//        txData = ABIEncoder.encodeMethodArguments("getIndexes", "qww");
//        result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);
//        System.out.println("indexes Data received "+result.getDecodedReturnData());

        txData = ABIEncoder.encodeMethodArguments("getIndex", "micro");
        result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);
        System.out.println("Index Data received "+result.getDecodedReturnData());

    }

    @Test
    public void testSearch() {
        // interaction with map
        //create a new account with initial balance to send the transaction
        Address sender = avmRule.getRandomAddress(BigInteger.valueOf(10_000_000L));

        byte[] txData = ABIEncoder.encodeMethodArguments("upload", "sdds", "magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        ResultCode status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("upload", "abcd", "magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("search", "sddsa");
        AvmRule.ResultWrapper result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);

        status = result.getReceiptStatus();
        Assert.assertTrue(status.isSuccess());

        System.out.println(((String[])result.getDecodedReturnData())[0]);

    }

    @Test
    public void testLinkIndex() {
        // interaction with map
        //create a new account with initial balance to send the transaction
        Address sender = avmRule.getRandomAddress(BigInteger.valueOf(10_000_000L));

        byte[] txData = ABIEncoder.encodeMethodArguments("upload", "sdds", "magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969");
        ResultCode status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        //check transaction is successful
        Assert.assertTrue(status.isSuccess());

        txData = ABIEncoder.encodeMethodArguments("getLinkByIndex", 0);
        AvmRule.ResultWrapper result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);

        Assert.assertEquals("magnet:?xt=urn:btih:3d381affbf1425ca6d03cb499941dae1ca73ba54&dn=KMSpico+12.3.24+FINAL+%2B+Portable+%28Office+and+Windows+10+Activato&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fzer0day.ch%3A1337&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969", result.getDecodedReturnData());

        txData = ABIEncoder.encodeMethodArguments("getLinkByIndex", 1);
        result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);

        Assert.assertEquals(null, result.getDecodedReturnData());
    }


    /*@Test
    public void testLogEvent() {
        byte[] txData = ABIEncoder.encodeMethodArguments("logEvent");
        AvmRule.ResultWrapper result = avmRule.call(from, dappAddr, BigInteger.ZERO, txData);

        Assert.assertTrue(result.getReceiptStatus().isSuccess());
        // get number of events generated
        assertEquals(2, result.getLogs().size());

        // get the Dapp adddress that generated the event
        assertEquals(dappAddr, result.getLogs().get(0).getSourceAddress());

        //read the event data
        assertArrayEquals("data1".getBytes(), result.getLogs().get(0).getData());

        //read the event topic
        assertArrayEquals(HashUtils.sha256("topic1".getBytes()), result.getLogs().get(1).getTopics().get(0));
    }*/

    /*@Test
    public void balanceTransfer() {
        // transfer value of 100 and ensure the balances are adjusted
        Address to = avmRule.getRandomAddress(BigInteger.ZERO);
        ResultCode status = avmRule.balanceTransfer(from, to, BigInteger.valueOf(100L), 1L).getReceiptStatus();

        assertTrue(status.isSuccess());
        assertEquals(BigInteger.valueOf(100L), avmRule.kernel.getBalance(to));
    }*/

    /*@Test
    public void checkCaller(){
        //non owner address
        Address sender = avmRule.getRandomAddress(BigInteger.valueOf(10_000_000L));
        byte[] txData = ABIEncoder.encodeMethodArguments("ownerSampleFunction");
        ResultCode status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        // require condition fails and the transaction status is set to Failed
        assertTrue(status.isFailed());
    }*/

    /*@Test
    public void insufficientBalance(){
        Address sender = avmRule.getRandomAddress(BigInteger.ZERO);
        byte[] txData = ABIEncoder.encodeMethodArguments("sum", 10, 10);
        ResultCode status = avmRule.call(sender, dappAddr, BigInteger.ZERO, txData).getReceiptStatus();
        // the sender does not have enough balance and the transaction is rejected
        assertTrue(status.isRejected());
    }*/
}
