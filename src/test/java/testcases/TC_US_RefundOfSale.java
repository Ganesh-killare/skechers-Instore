package testcases;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.jdom2.JDOMException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import requestbuilder.Close;

public class TC_US_RefundOfSale extends BaseClass {

	@Test(invocationCount = 2)
	public void TestCard01() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard02() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard03() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard04() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard05() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard06() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard07() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard08() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard09() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard10() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard11() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard12() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard13() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard14() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard15() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard16() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard17() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard18() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test(invocationCount = 2)
	public void TestCard19() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard20() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@Test()
	public void TestCard21() throws Exception, IOException, InterruptedException {
		String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("Method name: " + methodName);

		try {

			List<String> saleResult = performSaleTransaction(amount);

			if (saleResult.get(0).equalsIgnoreCase(approvalText)
					|| saleResult.get(0).equalsIgnoreCase(validationText)) {

				// Thread.sleep(3000);

				performRefundTransaction(saleResult);
			}
		} catch (Exception e) {
			System.out.println("we are not able to perform REFUND OF SALE transaction");
		}

	}

	@AfterMethod
	public void afterTransactionsOparations()
			throws UnknownHostException, IOException, InterruptedException, JDOMException {

		sendRequestToAESDK(Close.Request());
		receiveResponseFromAESDK();
		String className = this.getClass().getSimpleName();

		xl.saveExcelFile(FILE_NAME + "_" + className);

	}

}
