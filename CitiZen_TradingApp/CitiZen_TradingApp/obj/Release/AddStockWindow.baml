   M S B A M L   `   `   `  ���� M  ICitiZen_TradingApp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null7 clr-namespace:CitiZen_TradingAppCitiZen_TradingApp  '    !CitiZen_TradingApp.AddStockWindow   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     2local clr-namespace:CitiZen_TradingApp     -     � Title$  
Add Stocks��$	��500��$	��800�� � Icon$ citiIcon.ico�� � WindowStartupLocation$ CenterScreen=�.��� .+���  �� Orientation$ 
Horizontal=�$��20q�.+��� .+��� $��Stock Listing���� -    ��Name$ addStockListBox�� �� SelectionMode$ Single=�$	��100�� �� DisplayMemberPath$ Ticker�� �� SelectedItem �� *SelectedAddStock+�� $��10q�.+�!   CitiZen_TradingApp.Livedata  -     Name$ livedataChartAdd��$	��200��$	��600��$��10q��� $��10q�.+��� $ 
Horizontal=�.+��� $��Selected Stock:���� -   	 ��Name$	 tickerLabel��$
��Bold"�$��Center=����� 
 �� Path$
 SelectedAddStock.Ticker��� .���� $��	Underline{�$��Details���� $ 
Horizontal=�.+��� $
��Buy:���� $��Sell:���� $ 
Horizontal=�$��10q�.+��� $ ��Add Quantity to Portfolio:���� -    ��Name$ addStockVolumeTextBox��$��Center=�$��20��$��50���� -    ��Name$ addStockButton��$��20 0 0 0q�$��15��$��50��$	��Add��