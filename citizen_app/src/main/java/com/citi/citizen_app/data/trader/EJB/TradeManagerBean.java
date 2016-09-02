package com.citi.citizen_app.data.trader.EJB;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.repository.EJB.RepositoryPortfolioDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryPositionDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryStockDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryTradeDataBean;
import com.citi.citizen_app.model.Trade;

@Stateless
public class TradeManagerBean {

	@Inject
	private RepositoryPositionDataBean positionBean;
	@Inject
	private RepositoryTradeDataBean tradeBean;
	@Inject
	private RepositoryPortfolioDataBean portfolioBean;
	@Inject
	private RepositoryStockDataBean stockBean;


	//Pre Approval: Persist Trades "Pending"
	public void persistTradesPreApproval(int portfolioId,
			String ticker,
			String buyOrSell,
			String strategy,
			int sharesBoughtSold, 
			float price){

		String status = "PENDING";
		tradeBean.insertTradePreApproval(ticker, portfolioId, buyOrSell, price, sharesBoughtSold, strategy, status);
		System.out.println("INSERT TRADE PRE APPROVAL");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int tradeId = tradeBean.getLastTradeId();
		System.out.println("UPDATE TRADE POST APPROVAL: TRADEID: "+ tradeId);
		if (buyOrSell == "SELL") {
			sharesBoughtSold = sharesBoughtSold*-1;
			updateRepoPostApproval(portfolioId, ticker, strategy, tradeId, sharesBoughtSold, price);
		}else {
			updateRepoPostApproval(portfolioId, ticker, strategy, tradeId, sharesBoughtSold, price);
		}
		
	}

	/**
	 * ****Parameters from Strategy Bean******
	 * @param portfolioId 
	 * @param positionId
	 * @param stockId
	 * @param strategy
	 * ****Trade Execution Params****
	 * @param tradeId
	 * @param sharesBoughtSold <- If sold integer is negative.
	 * @param price
	 */
	public void updateRepoPostApproval(int portfolioId,
			String ticker,
			String strategy,
			int tradeId,
			int sharesBoughtSold, 
			float price) {

		getPositionIdsByPortfolioId(portfolioId);
		updateApprovedTrade(tradeId);
		updatePortfolioPositionPostApproval(getPositionIdsByPortfolioId(portfolioId), ticker, portfolioId, sharesBoughtSold);
	}


	private List<Integer> getPositionIdsByPortfolioId(int portfolioId) {
		return positionBean.getPositionIdsByPortfolioId(portfolioId);
	}


	/**
	 * update shares owned, portfolio's balance, profit/loss and position's profit/loss.
	 * @param positionIdList
	 * @param stockId
	 * @param portfolioId
	 * @param sharesBoughtSold
	 */
	public void updatePortfolioPositionPostApproval(List<Integer> positionIdList, String ticker, int portfolioId, int sharesBoughtSold) {

		BigDecimal posPfLoss = BigDecimal.ZERO;
		for (Integer positionId : positionIdList) {
			BigDecimal positionProfitLoss = BigDecimal.ZERO;
			if (positionBean==null) {
				System.out.println("positionBean is null.");
			} else {
				//update number of shares a position has.
				positionBean.updateShares(positionId, sharesBoughtSold);
				int stockId = stockBean.getStockIdByTicker(ticker);
				List<Trade> tradesList = tradeBean.getTradesByPosition(portfolioId, stockId);
				positionProfitLoss = calculateSharesProfitLoss(tradesList);

				//update a position's profitloss.
				positionBean.updatePositionsProfitLoss(positionId, positionProfitLoss);
			}
			posPfLoss = posPfLoss.add(positionProfitLoss);
		}
		if (portfolioBean==null) {
			System.out.println("portfolioBean is null.");
		} else {
			//updates portfolio cash balance and portfolio's profit/loss
			portfolioBean.updatePortfolioBalProfitLoss(portfolioId, posPfLoss);
		}
	}


	//update trade which was approved by Order Broker
	public void updateApprovedTrade(int tradeId) {
		if (tradeBean==null) {
			System.out.println("tradeBean is null.");
		} else {
			tradeBean.updateApprovedTrade(tradeId);
		}
	}



	private BigDecimal calculateSharesProfitLoss(List<Trade> tradesList) {
		BigDecimal totalBuyCost = BigDecimal.ZERO;
		BigDecimal totalSellIncome = BigDecimal.ZERO;
		for (Trade t : tradesList) {
			if (t.getBuySell() == "BUY") {
				totalBuyCost = totalBuyCost.add(multiplyInBD(t.getQuantity(),t.getPrice()));
			} else if (t.getBuySell() == "SELL") {
				totalSellIncome = totalSellIncome.add(multiplyInBD(t.getQuantity(),t.getPrice()));
			}
		}
		return totalSellIncome.subtract(totalBuyCost);
	}

	private BigDecimal multiplyInBD(int quantity, float price) {
		BigDecimal priceBD = new BigDecimal(Float.toString(price)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal valueBD = priceBD.multiply(new BigDecimal(quantity));
		return valueBD;
	}
}
