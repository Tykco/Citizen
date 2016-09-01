package com.citi.citizen_app.data.trader.EJB;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.repository.EJB.RepositoryPortfolioDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryPositionDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryTradeDataBean;
import com.citi.citizen_app.model.Trade;

@Stateless
public class ApprovedTradeManagerBean {

	@Inject
	private RepositoryPositionDataBean positionBean;
	@Inject
	private RepositoryTradeDataBean tradeBean;
	@Inject
	private RepositoryPortfolioDataBean portfolioBean;


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
	 * @param quantity
	 */
	public void updateRepoPostApproval(int portfolioId,
			int positionId,
			int stockId,
			String strategy,
			int tradeId,
			int sharesBoughtSold, 
			float price, 
			int quantity) {

		updatePortfolioPositionPostApproval(positionId, stockId, portfolioId, sharesBoughtSold);
		updateApprovedTrade(tradeId);
	}

	
	/**
	 * update shares owned, portfolio's balance, profit/loss and position's profit/loss.
	 * @param positionId
	 * @param stockId
	 * @param portfolioId
	 * @param sharesBoughtSold
	 */
	public void updatePortfolioPositionPostApproval(int positionId, int stockId, int portfolioId, int sharesBoughtSold) {
		BigDecimal positionProfitLoss = BigDecimal.ZERO;
		if (positionBean==null) {
			System.out.println("positionBean is null.");
		} else {
			positionBean.updateShares(positionId, sharesBoughtSold);

			List<Trade> tradesList = tradeBean.getTradesByPosition(portfolioId, stockId);
			positionProfitLoss = calculateSharesProfitLoss(tradesList);

			positionBean.updatePositionsProfitLoss(positionId, positionProfitLoss);
		}
		
		if (portfolioBean==null) {
			System.out.println("portfolioBean is null.");
		} else {
			//updates portfolio cash balance and portfolio's profit/loss
			portfolioBean.updatePortfolioBalProfitLoss(portfolioId, positionProfitLoss);
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
