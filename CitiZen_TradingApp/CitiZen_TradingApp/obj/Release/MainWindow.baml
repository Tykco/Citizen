   M S B A M L   `   `   `  ���� M  ICitiZen_TradingApp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null7 clr-namespace:CitiZen_TradingAppCitiZen_TradingApp  O Kmscorlib, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e0891&clr-namespace:System;assembly=mscorlibSystem #    CitiZen_TradingApp.MainWindow   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     @d2http://schemas.microsoft.com/expression/blend/2008     Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     2local clr-namespace:CitiZen_TradingApp     6sys&clr-namespace:System;assembly=mscorlib     -     � Title$  CitiZen Trading System��$	��766��$
��1366�� � WindowState$ 	Maximized=� � WindowStartupLocation$ CenterScreen=� � Icon$ citiIcon.ico�� � 	Resources *   $CitiZen_TradingApp.CurrentTimeTicker   currTimeTicker��%   &	          .��]� -    ]�Name$ mainDockPanel����. .+�o� -    o�Name$ mainMenu��$	��Top=�.@�m� -    m�Name$
 File��$
��File��.@�m� -   $	 New��$	��New��m� -   $
 Open��$
��Open��m� -   $
 Save��$
��Save���� -    ��Name$ 	statusBar��$��Bottom=�.@��� $��
Loading...��� -	   	 �Name$	 mainGrid��$��Center=�$��Center=�
 � RowDefinitions
 �� ��  � ColumnDefinitions �� �� .+��� 
���   �$��1q�._��� -
    ��Name$ portfolioStackPanel��$��10q�$��0��$��0��$	��360��$	��670��.+��� -   $ traderPortfolioStackPanel��.+���  �� Orientation$ 
Horizontal=�.+��� $
��Bold"�����  �� Path$ UserName� �� StringFormat$    {0}'s Portfolio ID: ���� $
��Bold"����� $ PortfolioId��� $ 
Horizontal=�.+��� $�� Portfolio created on:���� ���� $ Created��� $��0 -5 0 0q�$ 
Horizontal=�.+��� $��Center=�$�� Cash Balance:���� -    ��Name$ startBalanceLabel��$��Center=�$��14(�$
��Bold"��� $��	0 -15 0 0q�$ 
Horizontal=�.+��� $��Center=�$�� Profit / Loss:���� -   $ profitLossLabel��$��Center=�$��14(�$
��Bold"��� $��Center=�$��as of���� $��Center=�.���� ����  �� Source# [  $	 Now� �� Mode$ OneWay=��� -   $ portfolioButtons��$��Center=�$��Center=�$ 
Horizontal=�.+��� $��5 0 0 0q�$��Center=�$��Add Tickers (A,B,C)���� -    ��Name$ addStockTextBox��$��Center=�$��80��$��25���� -    ��Name$ addStockButton��$��10q�$��30��$��70��$��
Add Stocks���� -   $ removeStockButton��$��30��$��70��$��Remove Stock��&   System.Windows.Controls.DataGrid  -     Name$ portfolioDataGrid��$��16(�$
��Bold"�$	��200��$	��625��$��10q�   AutoGenerateColumns .     
IsReadOnly .    HeadersVisibility$ Column=�   CanUserReorderColumns .     CanUserSortColumns .     CanUserAddRows .     SelectionMode$ Single=���. $
��Auto=�$
��Auto=�   Columns 0  *System.Windows.Controls.DataGridTextColumn     
FontWeight$ Normal"�,  &System.Windows.Controls.DataGridColumn   Header$ Company Name��    MinWidth$	  200Q�1  +System.Windows.Controls.DataGridBoundColumn!   Binding! �� $ CompanyName�  $ Normal"�$ Ticker��$	  100Q�! �� $ Ticker�  $ Normal"�$ Shares��$	  100Q�! �� $ Shares�  $ Normal"�$ Profit / Loss��$	  100Q�"   Width5  /System.Windows.Controls.DataGridLengthConverter$" * ! �� $ SharesProfitLoss�$ {0:C}���� $��0��$��1��
���   �$��1q�._��� -   $ livedataStackPanel��$��10q�$	��360��$	��670��.+��� -   $ livedataHeader��$ 
Horizontal=�.+��� -   $ livedataLabel��$��Live Market Data��$
��Bold"��� $��Stock Symbol:���� -   # ��Name$# livedataComboBox��$ �� DisplayMemberPath$$ Ticker��$��Center=�$��Center=�!   CitiZen_TradingApp.Livedata  -   %  Name$% livedataChart��$��10q�$	��600��$	��240���� -   $ liveStockLabel��$��Center=�$��0 -5 0 0q�$
��Bold"�
����������� *Ticker+�� $��1��$��0��
���   �$��1q�._��� -   $ strategyStackPanel��$��5q�$	��370��$	��670��.+��� -   $ strategyHeader��$ 
Horizontal=�.+��� $��Trading Strategies��$
��Bold"��� $��Stock:���� -   $# strategyStockComboBox��$$ Ticker��$��Center=�$��Center=��� -   $��	0 -18 0 0q�$	��640�� & �� 
TargetType' �� Property( �� Value��%0   '��       �� "& ��.��� ' � ��$( Right=�.@��� ���� $��Two Moving Averages��$
��Bold"�.���� .+��� -   $ twoMAStrategyButtons��$ 
Horizontal=�$��0 3 0 0q�.+��� $5��/                                 Threshold (%):���� -   $ thresholdTextBox��$��Center=�$��50��$��50���� $��Volume:���� -   $ volumeTextBox��$��Center=�$��50��$��50���� $��Long MA:���� -    $ longMATextBox��$��Center=�$��50��$��50���� $��	Short MA:���� -!   $ shortMATextBox��$��Center=�$��50��$��50���� $ 
Horizontal=�$��	0 -5 0 -5q�.+��� $��Center=�$��    Selected Stock:���� -"   $ strategyStockLabel��$��30��$
��Bold"�$��Center=����� *Ticker+�� -#   $ activateTwoMAButton��$��25��$��10q�$��Activate Strategy��$��30���� -$   $ pauseResumeTwoMAButton��$��25��$��60��$��10q�$��Pause��$��30���� -%   $ deactivateTwoMAButton��$��25��{�.  $��10q�$��Deactivate Strategy��$��30���� ���� $��Bollinger Bands��$
��Bold"�.���� .+��� -&   $# bollingerBandsStrategyButtons��$ 
Horizontal=�$��0 3 0 0q�.+��� $��        Threshold (%):���� -'   $ thresholdBBTextBox��$��Center=�$��50��$��50���� $��Volume:���� -(   $ volumeBBTextBox��$��Center=�$��50��$��50���� $��Moving Average:���� -)   $ movingAvgBBTextBox��$��Center=�$��50��$��50���� $��Standard Deviation:���� -*   $ stdDevBBTextBox��$��Center=�$��50��$��50���� $ 
Horizontal=�$��	0 -5 0 -5q�.+��� $��Center=�$��    Selected Stock:���� -+   $ strategyStockBBLabel��$��30��$
��Bold"�$��Center=����� *Ticker+�� -,   $ activateBBButton��$��25��$��10q�$��Activate Strategy��$��30���� --   $ pauseResumeBBButton��$��25��$��60��$��10q�$��Pause��$��30���� -.   $ deactivateBBButton��$��25��{�.  $��10q�$��Deactivate Strategy��$��30���� $�� Orders��$
��Bold"��� .+�  -/   $ ordersDataGrid��$	��640�� .  $��14(�$
��Bold"�$	��180�� . $ Column=� .   .   .  ��. $
��Auto=�$
��Auto=�)   RowStyle) �� )  #System.Windows.Controls.DataGridRow"&  .��� ' �@J�IsHitTestVisible( .  * �� Triggers* �� + �� Binding+ �� $ BuySell�, �� Value, Sell.a��� ' � ��( �#E55B3C�� + �� $ BuySell�, Buy.a��� ' � ��
( ����   $ Normal"�$ Ticker��$  60Q�! �� $ Ticker�  $ Normal"�$ Strategy��$  80Q�! �� $ Strategy�  $ Normal"�$ Buy/Sell��$  60Q�-0   buySellColumn��! �� $ BuySell�  $ Normal"�$ Price��$  60Q�! �� $ Price�$ {0:C}��  $ Normal"�$ Quantity��$  60Q�! �� $ Quantity�  $ Normal"�$ Approved��$  60Q�! �� $ Approved�  $ Normal"�$ Time Purchased��$" * ! �� $ TimePurchased��� -1   - ��Name$- twoMACharting��$��1��$��1��
���   �$��1q�._��� $��Center=�.+��� $��5q�$��Center=�$��Center=�$��Trade Monitoring��$
��Bold"��� $��0 -5 0 0q�$ 
Horizontal=�$��Center=�$��Center=�.+��� $��Ticker:���� -2   $# twoMAStrategyComboBox��$��25��$$ Ticker���� $��5 0 0 0q�$��Selected Strategy:���� -3   $ selectedStrategyLabel��$��30��$
��Bold"��� $��Long MA:���� -4   $ longMALabel��$
��Bold"��� $��	Short MA:���� -5   $ shortMALabel��$
��Bold"�"	   CitiZen_TradingApp.TwoMAView	  -6   . 	 Name$. 
twoMAChart��$��10q�$	��600��$	��250���� -7   $- bollingerBandsCharting��$y�Hidden=�$��1��$��1��
���   �$��1q�._��� $��Center=�.+��� $��5q�$��Center=�$��Center=�$��Trade Monitoring��$
��Bold"��� $��0 -5 0 0q�$ 
Horizontal=�$��Center=�$��Center=�.+��� $��Ticker:���� -8   $# bBStrategyComboBox��$��25��$$ Ticker���� $��5 0 0 0q�$��Selected Strategy:���� -9   $ selectedBBStrategyLabel��$��30��$
��Bold"��� $	��MA:���� -:   $ movingAvgLabel��$
��Bold"��� $��Std Dev:���� -;   $ stdDevLabel��$
��Bold"�+
   %CitiZen_TradingApp.BollingerBandsView
  -<   / 
 Name$/ bollingerBandsChart��$��10q�$	��600��$	��250��