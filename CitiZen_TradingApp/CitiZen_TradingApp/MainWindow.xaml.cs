using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using LiveCharts;
using LiveCharts.Wpf;
using System.ComponentModel;
using System.Windows.Threading;
using LiveCharts.Configurations;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Net;
using System.Timers;

namespace CitiZen_TradingApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        #region Get Set Properties

        #region Portfolio Positions, Stock Listing
        private Portfolio traderPortfolio;
        public Portfolio TraderPortfolio
        {
            get { return traderPortfolio; }
            set
            {
                traderPortfolio = value;
                OnPropertyChanged("TraderPortfolio");
            }
        }

        private List<Position> portfolioPositions;
        public List<Position> PortfolioPositions
        {
            get { return portfolioPositions; }
            set
            {
                portfolioPositions = value;
                OnPropertyChanged("PortfolioPositions");
            }
        }

        private List<LiveFeed> stockListing;
        public List<LiveFeed> StockListing
        {
            get { return stockListing; }
            set
            {
                stockListing = value;
                OnPropertyChanged("StockListing");
            }
        }
        #endregion

        #region Add Stock Window Properties
        private AddStockWindow addStockWindow;
        public AddStockWindow AddStockWindow
        {
            get { return addStockWindow; }
            set
            {
                addStockWindow = value;
                OnPropertyChanged("AddStockWindow");
            }
        }

        private List<Livedata> livedataAddList;
        public List<Livedata> LivedataAddList
        {
            get { return livedataAddList; }
            set
            {
                livedataAddList = value;
                OnPropertyChanged("LivedataAddList");
            }
        }

        private LiveFeed selectedAddStock;
        public LiveFeed SelectedAddStock
        {
            get { return selectedAddStock; }
            set
            {
                selectedAddStock = value;
                OnPropertyChanged("SelectedAddStock");
            }
        }

        private Position toAddStock;
        public Position ToAddStock
        {
            get { return toAddStock; }
            set
            {
                toAddStock = value;
                OnPropertyChanged("ToAddStock");
            }
        }
        #endregion

        #region Portfolio Live Data Properties
        private List<Livedata> livedataList;
        public List<Livedata> LivedataList
        {
            get { return livedataList; }
            set
            {
                livedataList = value;
                OnPropertyChanged("LivedataList");
            }
        }

        private Livedata selectedLiveStockData;
        public Livedata SelectedLiveStockData
        {
            get { return selectedLiveStockData; }
            set
            {
                selectedLiveStockData = value;
                OnPropertyChanged("SelectedLiveStockData");
            }
        }

        private Position selectedLiveStock;
        public Position SelectedLiveStock
        {
            get { return selectedLiveStock; }
            set
            {
                selectedLiveStock = value;
                OnPropertyChanged("SelectedLiveStock");
            }
        }
        #endregion

        #region Performace Properties
        private Performance performance;
        public Performance Performance
        {
            get { return performance; }
            set
            {
                performance = value;
                OnPropertyChanged("Performance");
            }
        }

        private Position selectedPerfStock;
        public Position SelectedPerfStock
        {
            get { return selectedPerfStock; }
            set
            {
                selectedPerfStock = value;
                OnPropertyChanged("SelectedPerfStock");
            }
        }
        #endregion

        #region Strategy Properties
        private TwoMAStrategy selectedTwoMAStrategy;
        public TwoMAStrategy SelectedTwoMAStrategy
        {
            get { return selectedTwoMAStrategy; }
            set
            {
                selectedTwoMAStrategy = value;
                OnPropertyChanged("SelectedTwoMAStrategy");
            }
        }

        private List<TwoMAStrategy> twoMAStrategies;
        public List<TwoMAStrategy> TwoMAStrategies
        {
            get { return twoMAStrategies; }
            set
            {
                twoMAStrategies = value;
                OnPropertyChanged("TwoMAStrategies");
            }
        }

        private List<TwoMAView> twoMAViewList;
        public List<TwoMAView> TwoMAViewList
        {
            get { return twoMAViewList; }
            set
            {
                twoMAViewList = value;
                OnPropertyChanged("TwoMAViewList");
            }
        }

        private Position selectedStrategyStock;
        public Position SelectedStrategyStock
        {
            get { return selectedStrategyStock; }
            set
            {
                selectedStrategyStock = value;
                OnPropertyChanged("SelectedStrategyStock");
            }
        }

        private BollingerBandsStrategy selectedBollingerBandsStrategy;
        public BollingerBandsStrategy SelectedBollingerBandsStrategy
        {
            get { return selectedBollingerBandsStrategy; }
            set
            {
                selectedBollingerBandsStrategy = value;
                OnPropertyChanged("SelectedBollingerBandsStrategy");
            }
        }

        private List<BollingerBandsStrategy> bollingerBandsStrategies;
        public List<BollingerBandsStrategy> BollingerBandsStrategies
        {
            get { return bollingerBandsStrategies; }
            set
            {
                bollingerBandsStrategies = value;
                OnPropertyChanged("BollingerBandsStrategies");
            }
        }

        private List<BollingerBandsView> bollingerBandsViewList;
        public List<BollingerBandsView> BollingerBandsViewList
        {
            get { return bollingerBandsViewList; }
            set
            {
                bollingerBandsViewList = value;
                OnPropertyChanged("BollingerBandsViewList");
            }
        }
        #endregion

        #region Order Properties
        private List<Order> orderList;
        public List<Order> OrderList
        {
            get { return orderList; }
            set
            {
                orderList = value;
                OnPropertyChanged("OrderList");
            }
        }
        #endregion

        #endregion

        #region Timers 
        public Timer LiveDataTimerTest { get; set; }
        public Timer TwoMAStrategyTimerTest { get; set; }
        public Timer BollingerBandsTimerTest { get; set; }
        public Timer OrdersTimerTest { get; set; }
        public Timer PortfolioTimerTest { get; set; }
        public Timer PositionsTimerTest { get; set; }
        #endregion

        #region Dispatcher Timers 
        //public DispatcherTimer LiveDataTimer { get; set; }
        //public DispatcherTimer TwoMAStrategyTimer { get; set; }
        //public DispatcherTimer BollingerBandsTimer { get; set; }
        //public DispatcherTimer OrdersTimer { get; set; }
        //public DispatcherTimer PortfolioTimer { get; set; }

        //public DispatcherTimer LiveDataAddTimer { get; set; }
        //public DispatcherTimer PerformanceTimer { get; set; }

        //flag for Live Charts
        //public bool IsDataInjectionRunning { get; set; }
        #endregion

        #region Properties for REST Service
        public RESTService MainRESTService { get; set; }
        //private List<LiveFeed> restStockList;
        //public List<LiveFeed> RestStockList
        //{
        //    get { return restStockList; }
        //    set
        //    {
        //        restStockList = value;
        //        OnPropertyChanged("RestStockList");
        //    }
        //}
        #endregion

        public MainWindow()
        {
            InitializeComponent();

            #region Timers for Charts
            // Create a timer
            LiveDataTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            LiveDataTimerTest.Elapsed += new ElapsedEventHandler(LiveDataTimeTickerTest);
            // Set it to go off every five seconds
            LiveDataTimerTest.Interval = 1000;

            // Create a timer
            OrdersTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            OrdersTimerTest.Elapsed += new ElapsedEventHandler(OrdersTimeTickerTest);
            // Set it to go off every five seconds
            OrdersTimerTest.Interval = 1000;

            // Create a timer
            PortfolioTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            PortfolioTimerTest.Elapsed += new ElapsedEventHandler(PortfolioTimeTickerTest);
            // Set it to go off every five seconds
            PortfolioTimerTest.Interval = 1000;

            // Create a timer
            TwoMAStrategyTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            TwoMAStrategyTimerTest.Elapsed += new ElapsedEventHandler(TwoMAStrategyTimeTickerTest);
            // Set it to go off every five seconds
            TwoMAStrategyTimerTest.Interval = 1000;

            // Create a timer
            BollingerBandsTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            BollingerBandsTimerTest.Elapsed += new ElapsedEventHandler(BollingerBandsTimeTickerTest);
            // Set it to go off every five seconds
            BollingerBandsTimerTest.Interval = 1000;

            // Create a timer
            PositionsTimerTest = new Timer();
            // Tell the timer what to do when it elapses
            PositionsTimerTest.Elapsed += new ElapsedEventHandler(PositionsTimeTickerTest);
            // Set it to go off every five seconds
            PositionsTimerTest.Interval = 1000;
            #endregion

            #region Dispatcher Timers for Charts
            ////Timer for Live Data in Portfolio
            //LiveDataTimer = new DispatcherTimer
            //{
            //    Interval = TimeSpan.FromMilliseconds(1000)
            //};
            //LiveDataTimer.Tick += this.LiveDataTimeTicker;

            ////Timer for Two MA Strategy
            //TwoMAStrategyTimer = new DispatcherTimer
            //{
            //    Interval = TimeSpan.FromMilliseconds(1000)
            //};
            //TwoMAStrategyTimer.Tick += this.TwoMAStrategyTimeTicker;

            ////Timer for Bollinger Bands Strategy
            //BollingerBandsTimer = new DispatcherTimer
            //{
            //    Interval = TimeSpan.FromMilliseconds(1000)
            //};
            //BollingerBandsTimer.Tick += this.BollingerBandsTimeTicker;

            ////Timer for Orders Strategy
            //OrdersTimer = new DispatcherTimer
            //{
            //    Interval = TimeSpan.FromMilliseconds(1000)
            //};
            //OrdersTimer.Tick += this.OrdersTimeTicker;

            ////Timer for Orders Strategy
            //PortfolioTimer = new DispatcherTimer
            //{
            //    Interval = TimeSpan.FromMilliseconds(1000)
            //};
            //PortfolioTimer.Tick += this.PortfolioTimeTicker;

            //////Timer for Live Market Data to Add
            ////LiveDataAddTimer = new DispatcherTimer
            ////{
            ////    Interval = TimeSpan.FromMilliseconds(1000)
            ////};
            ////LiveDataAddTimer.Tick += this.LiveDataAddTimeTicker;
            ////LiveDataAddTimer.Start();

            //////Timer for Performance
            ////PerformanceTimer = new DispatcherTimer
            ////{
            ////    Interval = TimeSpan.FromMilliseconds(1000)
            ////};
            ////PerformanceTimer.Tick += this.PerformanceTimeTicker;
            ////PerformanceTimer.Start();
            #endregion

            #region Initialize REST Service
            MainRESTService = new RESTService();
            #endregion

            StockListing = new List<LiveFeed>();
            PortfolioPositions = new List<Position>(); 

            //bool aa = MainRESTService.GetLiveFeed("goog", 1);
            //if (aa)
            //{
            //    StockListing.Add(MainRESTService.ReturnLiveFeed.FirstOrDefault());
            //    Position pos = new Position { Ticker = MainRESTService.ReturnLiveFeed.FirstOrDefault().Ticker,
            //        CompanyName = MainRESTService.ReturnLiveFeed.FirstOrDefault().Ticker, LiveId = 1, Shares = 0,
            //        SharesProfitLoss = 0, User = new User {  UserId = 1000, UserName = "now"}
            //    };

            //    PortfolioPositions.Add(pos);

            //    LivedataList = new List<Livedata>();
            //    if (StockListing != null)
            //    {
            //        foreach (Position p in PortfolioPositions)
            //        {
            //            LivedataList.Add(new Livedata(StockListing.Where(l => l.Ticker == p.Ticker).FirstOrDefault()));
            //        }
            //    }

            //    SelectedLiveStock = PortfolioPositions.FirstOrDefault();

            //    foreach (Livedata l in LivedataList)
            //    {
            //        if (l.SLiveFeed != null && SelectedLiveStock != null)
            //        {
            //            if (l.SLiveFeed.Ticker == SelectedLiveStock.Ticker)
            //            {
            //                this.livedataChart.DataContext = l;
            //            }
            //        }

            //    }

            //    this.liveStockLabel.DataContext = SelectedLiveStock;
            //}

            #region Initialize User Portfolio
            TraderPortfolio = new Portfolio();
            bool checkTraderPortfolio = MainRESTService.GetUserPortfolio("EliteTrader");
            if (checkTraderPortfolio)
            {
                TraderPortfolio = MainRESTService.ReturnPortfolio;
            }
            //this.traderPortfolioStackPanel.DataContext = TraderPortfolio;
            this.startBalanceLabel.Text = String.Format("{0:C}", TraderPortfolio.StartBalance);
            this.profitLossLabel.Content = String.Format("{0:C}", TraderPortfolio.ProfitLoss);
            if (TraderPortfolio.ProfitLoss < 0)
            {
                this.profitLossLabel.Foreground = new SolidColorBrush(Colors.Red);
            }
            #endregion

            #region Initialize Portfolio Positions
            //PortfolioPositions = new List<Position>();
            bool checkPortfolioPositions = MainRESTService.GetPositions(TraderPortfolio.PortfolioId);
            if (checkPortfolioPositions)
            {
                PortfolioPositions = MainRESTService.ReturnPositions;
            }
            // And start it        
            //PositionsTimerTest.Enabled = true;

            //InitializePortfolioStocks();

            foreach (Position p in PortfolioPositions)
            {
                p.LiveId = 1;
            }
            this.portfolioDataGrid.ItemsSource = this.PortfolioPositions;
            //PortfolioTimer.Start();
            // And start it        
            //PortfolioTimerTest.Enabled = true;
            #endregion

            #region Initialize Stock Listing
            //StockListing = new List<LiveFeed>();
            foreach(Position p in PortfolioPositions)
            {
                bool checkLiveFeed = MainRESTService.GetLiveFeed(p.Ticker, p.LiveId);
                if (checkLiveFeed)
                {
                      StockListing.Add(MainRESTService.ReturnLiveFeed.FirstOrDefault());
                }
            }
            //InitializeStockListing();
            //LiveDataTimer.Start();
            // And start it        
            LiveDataTimerTest.Enabled = true;

            #endregion

            #region TODELETE: Initialize Sample User, Portfolio Positions, Stock Listing
            //TraderPortfolio = new Portfolio { UserName = "Andy", PortfolioId = 12345, StartBalance = 10.00, ProfitLoss = -10.00, Created = DateTime.Now.ToString() };
            //this.traderPortfolioStackPanel.DataContext = TraderPortfolio;
            //this.startBalanceLabel.Content = String.Format("{0:C}", TraderPortfolio.StartBalance);
            //this.profitLossLabel.Content = String.Format("{0:C}", TraderPortfolio.ProfitLoss);
            //if (TraderPortfolio.ProfitLoss < 0)
            //{
            //    this.profitLossLabel.Foreground = new SolidColorBrush(Colors.Red);
            //}

            //PortfolioPositions = new List<Position>();
            //InitializePortfolioStocks();
            //this.portfolioDataGrid.ItemsSource = this.PortfolioPositions;

            //StockListing = new List<LiveFeed>();
            //InitializeStockListing();
            #endregion

            #region Performance
            ////this.performanceComboBox.ItemsSource = this.PortfolioPositions;
            ////this.performanceComboBox.SelectedItem = this.PortfolioPositions.FirstOrDefault();

            //SelectedPerfStock = this.PortfolioPositions.FirstOrDefault();

            ////foreach (Stock s in Portfolio)
            ////{

            ////}

            //ChartValues<double> cv = new ChartValues<double>();
            //cv.Add(SelectedPerfStock.SharesProfitLoss);
            //cv.Add(SelectedPerfStock.SharesProfitLoss);
            //cv.Add(SelectedPerfStock.SharesProfitLoss);
            //cv.Add(SelectedPerfStock.SharesProfitLoss);
            //cv.Add(SelectedPerfStock.SharesProfitLoss);

            //LineSeries ls = new LineSeries
            //{
            //    Title = SelectedPerfStock.Ticker,
            //    Values = cv,
            //    LineSmoothness = 0,
            //    StrokeThickness = 2,
            //    PointGeometrySize = 5
            //};

            //Performance = new Performance();

            ////Performance.Labels = new[] { 
            ////                    string.Format("{0}:00", (DateTime.Now.Hour-4).ToString()), 
            ////                    string.Format("{0}:00", (DateTime.Now.Hour-3).ToString()), 
            ////                    string.Format("{0}:00", (DateTime.Now.Hour-2).ToString()), 
            ////                    string.Format("{0}:00", (DateTime.Now.Hour-1).ToString()), 
            ////                    string.Format("{0}:00", (DateTime.Now.Hour).ToString()) 
            ////                };

            //Performance.SeriesCollection.Add(ls);
            ////this.performanceChart.DataContext = Performance;
            ////this.stockSymbolLabel.DataContext = SelectedPerfStock;

            #endregion

            #region Live Data of Portfolio
            this.livedataComboBox.ItemsSource = PortfolioPositions;
            this.livedataComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();

            LivedataList = new List<Livedata>();
            if(StockListing != null)
            {
                StockListing.FirstOrDefault().Ticker = "goog";
                PortfolioPositions.FirstOrDefault().Ticker = "goog";

                foreach (Position p in PortfolioPositions)
                {
                    if(StockListing.Where(l => l.Ticker == p.Ticker).FirstOrDefault() !=null)
                    {
                        Livedata liveD = new Livedata { SLiveFeed = StockListing.Where(l => l.Ticker == p.Ticker).FirstOrDefault() };
                        LivedataList.Add(liveD);
                    }
                    
                }
            }

            SelectedLiveStock = PortfolioPositions.FirstOrDefault();

            //var chartStockLive = from l in LivedataList
            //                     where l.SLiveFeed.Ticker == SelectedLiveStock.Ticker
            //                     select l as Livedata;
            //if (chartStockLive != null && SelectedLiveStock != null && LivedataList.FirstOrDefault() != null)
            //{
            //    this.livedataChart.DataContext = LivedataList.Where(l => l.SLiveFeed.Ticker == SelectedLiveStock.Ticker).FirstOrDefault();
            //}

            foreach(Livedata l in LivedataList)
            {
                if(l.SLiveFeed != null && SelectedLiveStock != null)
                {
                    if (l.SLiveFeed.Ticker == SelectedLiveStock.Ticker)
                    {
                        this.livedataChart.DataContext = l;
                    }
                }

            }

            this.liveStockLabel.DataContext = SelectedLiveStock;
            #endregion

            #region Trading Strategies
            this.strategyStockComboBox.ItemsSource = PortfolioPositions;
            this.strategyStockComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();

            SelectedTwoMAStrategy = new TwoMAStrategy();
            TwoMAViewList = new List<TwoMAView>();
            TwoMAStrategies = new List<TwoMAStrategy>();

            SelectedStrategyStock = PortfolioPositions.FirstOrDefault();

            SelectedBollingerBandsStrategy = new BollingerBandsStrategy();
            BollingerBandsViewList = new List<BollingerBandsView>();
            BollingerBandsStrategies = new List<BollingerBandsStrategy>();
            #endregion

            #region Orders
            OrderList = new List<Order>();

            bool checkOrders = MainRESTService.GetOrdersByPortfolio(TraderPortfolio.PortfolioId);
            if (checkOrders)
            {
                OrderList = MainRESTService.ReturnOrders;
            }
            //InitializeOrders();

            this.ordersDataGrid.ItemsSource = OrderList;
            //OrdersTimer.Start();
            // And start it        
            //OrdersTimerTest.Enabled = true;
            #endregion

        }

        #region Time Tickers
        // Implement a call with the right signature for events going off
        private void LiveDataTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                for (int i = 0; i < StockListing.Count; i++)
                {
                    bool checkLiveFeed = MainRESTService.GetLiveFeed(StockListing[i].Ticker, StockListing[i].LiveId);
                    if (checkLiveFeed)
                    {
                        for (int j = 0; j < MainRESTService.ReturnLiveFeed.Count; j++)
                        {
                            StockListing[i] = MainRESTService.ReturnLiveFeed[j];
                        }
                    }
                }
            });

        }

        private void OrdersTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                bool checkOrders = MainRESTService.GetOrdersByPortfolio(TraderPortfolio.PortfolioId);
                if (checkOrders)
                {
                    OrderList = MainRESTService.ReturnOrders;
                }
                this.ordersDataGrid.Items.Refresh();
                this.ordersDataGrid.ItemsSource = OrderList;
                if (OrderList.All(o => o.BuySell.ToLower() == "sell"))
                {
                    this.ordersDataGrid.RowBackground = new SolidColorBrush(Colors.Red);
                }
                else
                {
                    this.ordersDataGrid.RowBackground = new SolidColorBrush(Colors.Green);
                }
            });

        }

        private void PortfolioTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                bool checkTraderPortfolio = MainRESTService.GetUserPortfolio("EliteTrader");
                if (checkTraderPortfolio)
                {
                    TraderPortfolio = MainRESTService.ReturnPortfolio;
                    TraderPortfolio.UserName = "EliteTrader";

                    if (TraderPortfolio.User.UserName != "EliteTrader")
                    {
                        this.traderPortfolioStackPanel.DataContext = TraderPortfolio;
                    }

                    this.startBalanceLabel.Text = String.Format("{0:C}", TraderPortfolio.StartBalance);
                    this.profitLossLabel.Content = String.Format("{0:C}", TraderPortfolio.ProfitLoss);

                }

                if (TraderPortfolio.ProfitLoss < 0)
                {
                    this.profitLossLabel.Foreground = new SolidColorBrush(Colors.Red);
                }
            });

        }

        private void TwoMAStrategyTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                for (int i = 0; i < TwoMAStrategies.Count; i++)
                {
                    string ticker = TwoMAStrategies[i].Ticker;
                    string strategy = "tma";
                    if (TwoMAStrategies[i].LiveMarketData != null)
                    {
                        int id = TwoMAStrategies[i].LiveMarketData.LiveId;
                        bool checkTwoMAStrategy = MainRESTService.GetStrategyValues(ticker, strategy, id);
                        if (checkTwoMAStrategy)
                        {
                            for (int j = 0; j < MainRESTService.ReturnTMAFeed.Count; j++)
                            {
                                TwoMAStrategies[i] = MainRESTService.ReturnTMAFeed[j];
                            }
                        }
                    }
                }
            });

        }

        private void BollingerBandsTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                for (int i = 0; i < BollingerBandsStrategies.Count; i++)
                {
                    string ticker = BollingerBandsStrategies[i].Ticker;
                    string strategy = "bb";
                    int id = BollingerBandsStrategies[i].LiveMarketData.LiveId;
                    bool checkBBStrategy = MainRESTService.GetStrategyValues(ticker, strategy, id);
                    if (checkBBStrategy)
                    {
                        for (int j = 0; j < MainRESTService.ReturnTMAFeed.Count; j++)
                        {
                            BollingerBandsStrategies[i] = MainRESTService.ReturnBBFeed[j];
                        }
                    }
                }
            });

        }

        private void PositionsTimeTickerTest(object source, ElapsedEventArgs e)
        {
            App.Current.Dispatcher.Invoke((Action)delegate
            {
                bool checkPortfolioPositions = MainRESTService.GetPositions(TraderPortfolio.PortfolioId);
                if (checkPortfolioPositions)
                {
                    PortfolioPositions = MainRESTService.ReturnPositions;
                }

                if (PortfolioPositions != null)
                {
                    //this.portfolioDataGrid.ItemsSource = this.PortfolioPositions;
                    //this.portfolioDataGrid.Items.Refresh();
                }
            });

        }
        #endregion

        #region Dispatch Time Tickers
        ////Time Ticker for Live Data in Portfolio
        //private void LiveDataTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    for (int i = 0; i < StockListing.Count; i++) 
        //    {
        //        bool checkLiveFeed = MainRESTService.GetLiveFeed(StockListing[i].Ticker, StockListing[i].LiveId);
        //        if (checkLiveFeed)
        //        {
        //            for (int j = 0; j < MainRESTService.ReturnLiveFeed.Count; j++)
        //            {
        //                StockListing[i] = MainRESTService.ReturnLiveFeed[j];
        //            }
        //        }
        //    }

        //    //Random r = new Random();
        //    //foreach (Position p in PortfolioPositions)
        //    //{
        //    //    StockListing.Where(l => l.Ticker == p.Ticker).FirstOrDefault().Price += (double)r.Next(-100, 100) / 1000;
        //    //}

        //}

        ////Time Ticker for Two MA Strategy Trading
        //private void TwoMAStrategyTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    for (int i = 0; i < TwoMAStrategies.Count; i++)
        //    {
        //        string ticker = TwoMAStrategies[i].Ticker;
        //        string strategy = "tma";
        //        if(TwoMAStrategies[i].LiveMarketData != null)
        //        {
        //            int id = TwoMAStrategies[i].LiveMarketData.LiveId;
        //            bool checkTwoMAStrategy = MainRESTService.GetStrategyValues(ticker, strategy, id);
        //            if (checkTwoMAStrategy)
        //            {
        //                for (int j = 0; j < MainRESTService.ReturnTMAFeed.Count; j++)
        //                {
        //                    TwoMAStrategies[i] = MainRESTService.ReturnTMAFeed[j];
        //                }
        //            }
        //        }
        //    }

        //}

        ////Time Ticker for Bollinger Bands Strategy Trading
        //private void BollingerBandsTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    for (int i = 0; i < BollingerBandsStrategies.Count; i++)
        //    {
        //        string ticker = BollingerBandsStrategies[i].Ticker;
        //        string strategy = "bb";
        //        int id = BollingerBandsStrategies[i].LiveMarketData.LiveId;
        //        bool checkBBStrategy = MainRESTService.GetStrategyValues(ticker, strategy, id);
        //        if (checkBBStrategy)
        //        {
        //            for (int j = 0; j < MainRESTService.ReturnTMAFeed.Count; j++)
        //            {
        //                BollingerBandsStrategies[i] = MainRESTService.ReturnBBFeed[j];
        //            }
        //        }
        //    }

        //}

        ////Time Ticker for Orders
        //private void OrdersTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    bool checkOrders = MainRESTService.GetOrdersByPortfolio(TraderPortfolio.PortfolioId);
        //    if (checkOrders)
        //    {
        //        OrderList = MainRESTService.ReturnOrders;
        //    }
        //    this.ordersDataGrid.Items.Refresh();
        //    this.ordersDataGrid.ItemsSource = OrderList;
        //    if (OrderList.All(o => o.BuySell.ToLower() == "sell"))
        //    {
        //        this.ordersDataGrid.RowBackground = new SolidColorBrush(Colors.Red);
        //    }
        //    else
        //    {
        //        this.ordersDataGrid.RowBackground = new SolidColorBrush(Colors.Green);
        //    }

        //}

        ////Time Ticker for User Portfolio
        //private void PortfolioTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    bool checkTraderPortfolio = MainRESTService.GetUserPortfolio("EliteTrader");
        //    if (checkTraderPortfolio)
        //    {
        //        TraderPortfolio = MainRESTService.ReturnPortfolio;
        //    }
        //    this.traderPortfolioStackPanel.DataContext = TraderPortfolio;
        //    this.startBalanceLabel.Content = String.Format("{0:C}", TraderPortfolio.StartBalance);
        //    this.profitLossLabel.Content = String.Format("{0:C}", TraderPortfolio.ProfitLoss);
        //    if (TraderPortfolio.ProfitLoss < 0)
        //    {
        //        this.profitLossLabel.Foreground = new SolidColorBrush(Colors.Red);
        //    }

        //}

        ////Time Ticker for Live Market Data to Add
        //private void LiveDataAddTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    //TODO: Make REST calls to get objects

        //    Random r = new Random();
        //    foreach (LiveFeed l in StockListing)
        //    {
        //        l.Price += (double)r.Next(-100, 100) / 1000;
        //    }
        //}

        ////Time Ticker for Performance
        //private void PerformanceTimeTicker(object sender, EventArgs eventArgs)
        //{
        //    //stub
        //}
        #endregion

        #region Initialize Samples

        private void InitializePortfolioStocks()
        {
            PortfolioPositions.Add(new Position { CompanyName = "IBM Machines", Ticker = "IBM", Shares = 1000, SharesProfitLoss = 0.00 });
            PortfolioPositions.Add(new Position { CompanyName = "Citigroup", Ticker = "C", Shares = 2000, SharesProfitLoss = 0.00 });
            PortfolioPositions.Add(new Position { CompanyName = "Apple", Ticker = "APPL", Shares = 3000, SharesProfitLoss = 0.00 });
            PortfolioPositions.Add(new Position { CompanyName = "Swansea City", Ticker = "SWAN", Shares = 5000, SharesProfitLoss = 0.00 });
            PortfolioPositions.Add(new Position { CompanyName = "Man Utd Football Club", Ticker = "MUFC", Shares = 2000, SharesProfitLoss = 0.00 });
            //Portfolio.Add(new Position { CompanyName = "LOL Utd Football Club", Ticker = "LUFC", Shares = 2000, SharesProfitLoss = 0.00 });
            //Portfolio.Add(new Position { CompanyName = "GG Utd Football Club", Ticker = "GGFC", Shares = 2000, SharesProfitLoss = 0.00 });
        }

        private void InitializeStockListing()
        {
            StockListing.Add(new LiveFeed { Ticker = "IBM", Open = 12.24, Price = 12.24, Ask = 12.24, Bid = 12.24 });
            StockListing.Add(new LiveFeed { Ticker = "C", Open = 46.65, Price = 46.65, Ask = 46.65, Bid = 46.65 });
            StockListing.Add(new LiveFeed { Ticker = "APPL", Open = 33.66, Price = 33.66, Ask = 33.66, Bid = 33.66 });
            StockListing.Add(new LiveFeed { Ticker = "SWAN", Open = 39.54, Price = 39.54, Ask = 39.54, Bid = 39.54 });
            StockListing.Add(new LiveFeed { Ticker = "MUFC", Open = 23.67, Price = 23.67, Ask = 23.67, Bid = 23.67 });
            StockListing.Add(new LiveFeed { Ticker = "LCFC", Open = 23.67, Price = 23.67, Ask = 23.67, Bid = 23.67 });
            StockListing.Add(new LiveFeed { Ticker = "DB", Open = 23.67, Price = 23.67, Ask = 23.67, Bid = 23.67 });
        }

        private void InitializeOrders()
        {
            for (int i = 0; i < 5; i++)
            {
                Order o = new Order { Ticker = "C", Strategy = "Two MA", Price = 46.73, BuySell = "Buy", Quantity = 1000, Approved = "Yes", TimePurchased = DateTime.Now.ToString() };
                OrderList.Add(o);
            }

            for (int i = 0; i < 5; i++)
            {
                Order o = new Order { Ticker = "C", Strategy = "Two MA", Price = 46.73, BuySell = "Sell", Quantity = 1000, Approved = "Yes", TimePurchased = DateTime.Now.ToString() };
                OrderList.Add(o);
            }
        }

        #endregion

        private void livedataComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var comboBox = sender as ComboBox;
            Position sPosition = comboBox.SelectedItem as Position;
            SelectedLiveStock = sPosition as Position;

            if (SelectedLiveStock == null)
            {
                SelectedLiveStock = PortfolioPositions.FirstOrDefault();
                this.livedataComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();
            }

            if (LivedataList != null && PortfolioPositions.Count > 0)
            {
                //LivedataList.Where(l => l.SLiveFeed.Ticker == SelectedLiveStock.Ticker).Equals(SelectedLiveStock as Position);

                this.livedataChart.DataContext = LivedataList.Where(l => l.SLiveFeed.Ticker == SelectedLiveStock.Ticker);
                this.livedataComboBox.ItemsSource = PortfolioPositions;
                this.liveStockLabel.DataContext = SelectedLiveStock;
            }
        }

        private void strategyStockComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var comboBox = sender as ComboBox;
            Position sPosition = comboBox.SelectedItem as Position;
            SelectedStrategyStock = sPosition as Position;

            if (TwoMAStrategies != null)
            {
                TwoMAStrategy sTwoMAStrategy = TwoMAStrategies.Where(t => t.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault();

                if (sTwoMAStrategy != null)
                {
                    if (!sTwoMAStrategy.IsNotActivated)
                    {
                        this.activateTwoMAButton.IsEnabled = false;
                        this.deactivateTwoMAButton.IsEnabled = true;
                        this.pauseResumeTwoMAButton.IsEnabled = true;

                        this.thresholdTextBox.IsReadOnly = true;
                        this.volumeTextBox.IsReadOnly = true;
                        this.longMATextBox.IsReadOnly = true;
                        this.shortMATextBox.IsReadOnly = true;
                        this.thresholdTextBox.FontWeight = FontWeights.Bold;
                        this.volumeTextBox.FontWeight = FontWeights.Bold;
                        this.longMATextBox.FontWeight = FontWeights.Bold;
                        this.shortMATextBox.FontWeight = FontWeights.Bold;
                    }
                    else
                    {
                        this.activateTwoMAButton.IsEnabled = true;
                        this.pauseResumeTwoMAButton.IsEnabled = false;
                        this.deactivateTwoMAButton.IsEnabled = false;

                        this.thresholdTextBox.IsReadOnly = false;
                        this.volumeTextBox.IsReadOnly = false;
                        this.longMATextBox.IsReadOnly = false;
                        this.shortMATextBox.IsReadOnly = false;
                        this.thresholdTextBox.FontWeight = FontWeights.Normal;
                        this.volumeTextBox.FontWeight = FontWeights.Normal;
                        this.longMATextBox.FontWeight = FontWeights.Normal;
                        this.shortMATextBox.FontWeight = FontWeights.Normal;
                    }
                }
                else
                {
                    this.activateTwoMAButton.IsEnabled = true;
                    this.pauseResumeTwoMAButton.IsEnabled = false;
                    this.deactivateTwoMAButton.IsEnabled = false;

                    this.thresholdTextBox.IsReadOnly = false;
                    this.volumeTextBox.IsReadOnly = false;
                    this.longMATextBox.IsReadOnly = false;
                    this.shortMATextBox.IsReadOnly = false;
                    this.thresholdTextBox.FontWeight = FontWeights.Normal;
                    this.volumeTextBox.FontWeight = FontWeights.Normal;
                    this.longMATextBox.FontWeight = FontWeights.Normal;
                    this.shortMATextBox.FontWeight = FontWeights.Normal;
                }

            }
                    
            this.strategyStockLabel.DataContext = SelectedStrategyStock;

            if (BollingerBandsStrategies != null)
            {
                BollingerBandsStrategy sBollingerBandsStrategy = BollingerBandsStrategies.Where(b => b.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault();

                if (sBollingerBandsStrategy != null)
                {
                    if (!sBollingerBandsStrategy.IsNotActivated)
                    {
                        this.activateBBButton.IsEnabled = false;
                        this.deactivateBBButton.IsEnabled = true;
                        this.pauseResumeBBButton.IsEnabled = true;

                        this.thresholdBBTextBox.IsReadOnly = true;
                        this.volumeBBTextBox.IsReadOnly = true;
                        this.movingAvgBBTextBox.IsReadOnly = true;
                        this.stdDevBBTextBox.IsReadOnly = true;
                        this.thresholdBBTextBox.FontWeight = FontWeights.Bold;
                        this.volumeBBTextBox.FontWeight = FontWeights.Bold;
                        this.movingAvgBBTextBox.FontWeight = FontWeights.Bold;
                        this.stdDevBBTextBox.FontWeight = FontWeights.Bold;
                    }
                    else
                    {
                        this.activateBBButton.IsEnabled = true;
                        this.pauseResumeBBButton.IsEnabled = false;
                        this.deactivateBBButton.IsEnabled = false;

                        this.thresholdBBTextBox.IsReadOnly = false;
                        this.volumeBBTextBox.IsReadOnly = false;
                        this.movingAvgBBTextBox.IsReadOnly = false;
                        this.stdDevBBTextBox.IsReadOnly = false;
                        this.thresholdBBTextBox.FontWeight = FontWeights.Normal;
                        this.volumeBBTextBox.FontWeight = FontWeights.Normal;
                        this.movingAvgBBTextBox.FontWeight = FontWeights.Normal;
                        this.stdDevBBTextBox.FontWeight = FontWeights.Normal;
                    }
                }
                else
                {
                    this.activateBBButton.IsEnabled = true;
                    this.pauseResumeBBButton.IsEnabled = false;
                    this.deactivateBBButton.IsEnabled = false;

                    this.thresholdBBTextBox.IsReadOnly = false;
                    this.volumeBBTextBox.IsReadOnly = false;
                    this.movingAvgBBTextBox.IsReadOnly = false;
                    this.stdDevBBTextBox.IsReadOnly = false;
                    this.thresholdBBTextBox.FontWeight = FontWeights.Normal;
                    this.volumeBBTextBox.FontWeight = FontWeights.Normal;
                    this.movingAvgBBTextBox.FontWeight = FontWeights.Normal;
                    this.stdDevBBTextBox.FontWeight = FontWeights.Normal;
                }

            }

        }

        private void addStockButton_Click(object sender, RoutedEventArgs e)
        {
            string tickersToAdd = this.addStockTextBox.Text;
            bool checkInitLiveFeed = MainRESTService.InitLiveFeed(tickersToAdd);
            string[] tickerSymbols = tickersToAdd.Split(',');

            if(!tickerSymbols.FirstOrDefault().Equals(""))
            {
                for (int i = 0; i < tickerSymbols.Count(); i++)
                {
                    bool checkLiveFeed = MainRESTService.GetLiveFeed(tickerSymbols[i], 0);
                    if (checkLiveFeed)
                    {
                        StockListing.Add(MainRESTService.ReturnLiveFeed.FirstOrDefault());
                    }
                    PortfolioPositions.Add(MainRESTService.ReturnLiveFeed.FirstOrDefault().Stock);
                    LivedataList.Add(new Livedata(StockListing.Where(l => l.Ticker == tickerSymbols[i]).FirstOrDefault()));

                    this.livedataComboBox.Items.Refresh();
                    this.portfolioDataGrid.Items.Refresh();
                }
            }

        }

        private void removeStockButton_Click(object sender, RoutedEventArgs e)
        {
            Position selectedStock = this.portfolioDataGrid.SelectedItem as Position;

            if (selectedStock != null)
            {
                MessageBoxResult msgBoxResult = MessageBox.Show("Are you sure you want to remove the selected stock from your portfolio?", "Warning!", MessageBoxButton.OKCancel, MessageBoxImage.Asterisk);
                if (msgBoxResult == MessageBoxResult.OK)
                {
                    this.PortfolioPositions.Remove(this.PortfolioPositions.Where(s => s.Ticker == selectedStock.Ticker).FirstOrDefault());
                    this.portfolioDataGrid.Items.Refresh();
                    //this.performanceComboBox.Items.Refresh();
                    this.livedataComboBox.Items.Refresh();

                    if (SelectedLiveStock != null)
                    {
                        LivedataList.Remove(LivedataList.Where(l => l.SLiveFeed.Ticker == SelectedLiveStock.Ticker).FirstOrDefault());
                    }

                }
            }
            else
            {
                MessageBox.Show("Please select a stock to remove", "Error!");
            }

        }

        private void AddStockWindow_Closed(object sender, EventArgs e)
        {
            this.AddStockWindow = null;
        }

        private void Window_Closing(object sender, CancelEventArgs e)
        {
            //LiveDataTimer.Stop();
            //OrdersTimer.Stop();
            //PortfolioTimer.Stop();

            //LiveDataTimer = null;
            //TwoMAStrategyTimer = null;
            //BollingerBandsTimer = null;
            //OrdersTimer = null;
            //PortfolioTimer = null;

            LiveDataTimerTest.Enabled = false;
            OrdersTimerTest.Enabled = false;
            PortfolioTimerTest.Enabled = false;
            PositionsTimerTest.Enabled = false;

            LiveDataTimerTest = null;
            TwoMAStrategyTimerTest = null;
            BollingerBandsTimerTest = null;
            OrdersTimerTest = null;
            PortfolioTimerTest = null;
            PositionsTimerTest = null;

            //if (this.AddStockWindow != null && this.AddStockWindow.IsActive)
            //{
            //    this.AddStockWindow.Close();
            //}

            LivedataAddList = null;
            LivedataList = null;
            AddStockWindow = null;
            PortfolioPositions = null;
            StockListing = null;
            TwoMAStrategies = null;
            TwoMAViewList = null;
            OrderList = null;
        }

        private void TextBox_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            int num;
            if (!int.TryParse(e.Text, out num) && e.Text != ".")
            {
                e.Handled = true;
            }
        }

        private void activateTwoMAButton_Click(object sender, RoutedEventArgs e)
        {
            Position sPosition = this.strategyStockComboBox.SelectedItem as Position;
            SelectedStrategyStock = sPosition;

            if(SelectedStrategyStock != null)
            {
                if (TwoMAStrategies.Where(t => t.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() == null)
                {
                    double threshold;
                    int longMA;
                    int shortMA;
                    int volume;
                    double.TryParse(this.thresholdTextBox.Text, out threshold);
                    int.TryParse(this.longMATextBox.Text, out longMA);
                    int.TryParse(this.shortMATextBox.Text, out shortMA);
                    int.TryParse(this.volumeTextBox.Text, out volume);

                    bool checkStartTMA = MainRESTService.StartTMA(SelectedStrategyStock.Ticker, longMA, shortMA, volume, threshold, TraderPortfolio.PortfolioId);

                    SelectedTwoMAStrategy = new TwoMAStrategy
                    {
                        Ticker = SelectedStrategyStock.Ticker,
                        Price = StockListing.Where(l => l.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault().Price,
                        Threshold = threshold,
                        LongMA = longMA,
                        ShortMA = shortMA,
                        Volume = volume,
                        LongMAPrice = 0,
                        ShortMAPrice = 0,
                        IsNotActivated = false
                    };

                    TwoMAStrategies.Add(SelectedTwoMAStrategy);
                    TwoMAViewList.Add(new TwoMAView(SelectedTwoMAStrategy));

                }
                else
                {
                    SelectedTwoMAStrategy = TwoMAStrategies.Where(t => t.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() as TwoMAStrategy;
                    SelectedTwoMAStrategy.IsNotActivated = false;
                }

                this.longMALabel.Content = SelectedTwoMAStrategy.LongMA;
                this.shortMALabel.Content = SelectedTwoMAStrategy.ShortMA;

                this.twoMAChart.DataContext = TwoMAViewList.Where(t => t.StrategyTwoMA.Ticker == SelectedTwoMAStrategy.Ticker).FirstOrDefault();
                this.twoMAStrategyComboBox.ItemsSource = TwoMAStrategies;
                this.twoMAStrategyComboBox.SelectedItem = SelectedTwoMAStrategy;
                this.twoMAStrategyComboBox.Items.Refresh();

                this.activateTwoMAButton.IsEnabled = false;
                this.deactivateTwoMAButton.IsEnabled = true;
                this.pauseResumeTwoMAButton.IsEnabled = true;

                this.thresholdTextBox.IsReadOnly = true;
                this.volumeTextBox.IsReadOnly = true;
                this.longMATextBox.IsReadOnly = true;
                this.shortMATextBox.IsReadOnly = true;
                this.thresholdTextBox.FontWeight = FontWeights.Bold;
                this.volumeTextBox.FontWeight = FontWeights.Bold;
                this.longMATextBox.FontWeight = FontWeights.Bold;
                this.shortMATextBox.FontWeight = FontWeights.Bold;

            }

            //TwoMAStrategyTimer.Start();
            // And start it        
            TwoMAStrategyTimerTest.Enabled = true;
        }

        private void pauseResumeTwoMAButton_Click(object sender, RoutedEventArgs e)
        {
            if(this.pauseResumeTwoMAButton.Content.Equals("Resume"))
            {
                this.pauseResumeTwoMAButton.Content = "Pause";
            }
            else
            {
                this.pauseResumeTwoMAButton.Content = "Resume";
            }  
        }

        private void deactivateTwoMAButton_Click(object sender, RoutedEventArgs e)
        {
            SelectedStrategyStock = this.strategyStockComboBox.SelectedItem as Position;
            SelectedTwoMAStrategy = this.TwoMAStrategies.Where(t => t.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() as TwoMAStrategy;

            this.TwoMAViewList.Remove(this.TwoMAViewList.Where(t => t.StrategyTwoMA.Ticker == SelectedTwoMAStrategy.Ticker).FirstOrDefault());
            this.TwoMAStrategies.Remove(this.TwoMAStrategies.Where(t => t.Ticker == SelectedTwoMAStrategy.Ticker).FirstOrDefault());
            this.twoMAStrategyComboBox.Items.Refresh();
            if(TwoMAStrategies.Count > 0)
            {
                this.SelectedTwoMAStrategy = TwoMAStrategies.FirstOrDefault();
                this.twoMAChart.DataContext = TwoMAViewList.Where(t => t.StrategyTwoMA.Ticker == SelectedTwoMAStrategy.Ticker).FirstOrDefault();
                this.twoMAStrategyComboBox.ItemsSource = TwoMAStrategies;
                this.twoMAStrategyComboBox.SelectedItem = this.SelectedTwoMAStrategy;
            }
            else
            {
                this.twoMAChart.DataContext = null;
            }

            this.activateTwoMAButton.IsEnabled = true;
            this.pauseResumeTwoMAButton.IsEnabled = false;
            this.deactivateTwoMAButton.IsEnabled = false;

            this.thresholdTextBox.IsReadOnly = false;
            this.volumeTextBox.IsReadOnly = false;
            this.longMATextBox.IsReadOnly = false;
            this.shortMATextBox.IsReadOnly = false;
            this.thresholdTextBox.FontWeight = FontWeights.Normal;
            this.volumeTextBox.FontWeight = FontWeights.Normal;
            this.longMATextBox.FontWeight = FontWeights.Normal;
            this.shortMATextBox.FontWeight = FontWeights.Normal;

            //TwoMAStrategyTimer.Stop();
            TwoMAStrategyTimerTest.Stop();
        }

        private void twoMAStrategyComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var comboBox = sender as ComboBox;
            TwoMAStrategy sTwoMAStrategy = comboBox.SelectedItem as TwoMAStrategy;
            SelectedTwoMAStrategy = sTwoMAStrategy as TwoMAStrategy;

            if(SelectedTwoMAStrategy != null)
            {
                this.strategyStockLabel.DataContext = SelectedStrategyStock;
                this.twoMAStrategyComboBox.ItemsSource = TwoMAStrategies;
                this.twoMAChart.DataContext = TwoMAViewList.Where(t => t.StrategyTwoMA.Ticker == SelectedTwoMAStrategy.Ticker).FirstOrDefault();
            }

        }
        
        private void TabControl_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var tabControl = sender as TabControl;
            string sHeader = tabControl.SelectedItem.ToString() as string; 
           
            if(sHeader.Contains("Two Moving Averages"))
            {
                this.selectedStrategyLabel.Content = "Two Moving Averages";
                this.twoMACharting.Visibility = Visibility.Visible;
                this.bollingerBandsCharting.Visibility = Visibility.Hidden;
            }
            else if(sHeader.Contains("Bollinger Bands"))
            {
                this.selectedBBStrategyLabel.Content = "Bollinger Bands";
                this.twoMACharting.Visibility = Visibility.Hidden;
                this.bollingerBandsCharting.Visibility = Visibility.Visible;
            }
            else
            {
                this.twoMACharting.Visibility = Visibility.Visible;
                this.bollingerBandsCharting.Visibility = Visibility.Hidden;
            }

        }

        private void portfolioDataGrid_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var dataGrid = sender as DataGrid;
            Position sPosition = dataGrid.SelectedItem as Position;
            SelectedLiveStock = sPosition as Position;

            if (SelectedLiveStock == null)
            {
                SelectedLiveStock = PortfolioPositions.FirstOrDefault();
                this.livedataComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();
            }

            if (LivedataList != null && PortfolioPositions.Count > 0)
            {
                this.livedataChart.DataContext = LivedataList.Where(l => l.SLiveFeed.Ticker == SelectedLiveStock.Ticker);
                this.livedataComboBox.ItemsSource = PortfolioPositions;
                this.liveStockLabel.DataContext = SelectedLiveStock;
                this.livedataComboBox.SelectedItem = SelectedLiveStock;
            }
        }

        private void activateBBButton_Click(object sender, RoutedEventArgs e)
        {
            Position sPosition = this.strategyStockComboBox.SelectedItem as Position;
            SelectedStrategyStock = sPosition;

            if (SelectedStrategyStock != null)
            {
                if (BollingerBandsStrategies.Where(b => b.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() == null)
                {
                    double threshold;
                    int volume;
                    double movingAvg;
                    double stdDev;
                    double.TryParse(this.thresholdBBTextBox.Text, out threshold);
                    int.TryParse(this.volumeBBTextBox.Text, out volume);
                    double.TryParse(this.movingAvgBBTextBox.Text, out movingAvg);
                    double.TryParse(this.stdDevBBTextBox.Text, out stdDev);

                    bool checkStartBB = MainRESTService.StartBB(SelectedStrategyStock.Ticker, movingAvg, volume, threshold, stdDev, TraderPortfolio.PortfolioId);

                    SelectedBollingerBandsStrategy = new BollingerBandsStrategy
                    {
                        Ticker = SelectedStrategyStock.Ticker,
                        Price = StockListing.Where(l => l.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault().Price,
                        Threshold = threshold,
                        MovingAvg = movingAvg,
                        Volume = volume,
                        StdDev = stdDev,
                        UpperBand = 0,
                        MiddleBand = 0,
                        LowerBand = 0,
                        IsNotActivated = false
                    };

                    BollingerBandsStrategies.Add(SelectedBollingerBandsStrategy);
                    BollingerBandsViewList.Add(new BollingerBandsView(SelectedBollingerBandsStrategy));

                }
                else
                {
                    SelectedBollingerBandsStrategy = BollingerBandsStrategies.Where(b => b.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() as BollingerBandsStrategy;
                    SelectedBollingerBandsStrategy.IsNotActivated = false;
                }

                this.movingAvgLabel.Content = SelectedBollingerBandsStrategy.MovingAvg;
                this.stdDevLabel.Content = SelectedBollingerBandsStrategy.MovingAvg;

                this.bollingerBandsChart.DataContext = BollingerBandsViewList.Where(t => t.StrategyBollingerBands.Ticker == SelectedBollingerBandsStrategy.Ticker).FirstOrDefault();
                this.bBStrategyComboBox.ItemsSource = BollingerBandsStrategies;
                this.bBStrategyComboBox.SelectedItem = SelectedBollingerBandsStrategy;
                this.bBStrategyComboBox.Items.Refresh();

                this.activateBBButton.IsEnabled = false;
                this.deactivateBBButton.IsEnabled = true;
                this.pauseResumeBBButton.IsEnabled = true;

                this.thresholdBBTextBox.IsReadOnly = true;
                this.volumeBBTextBox.IsReadOnly = true;
                this.movingAvgBBTextBox.IsReadOnly = true;
                this.stdDevBBTextBox.IsReadOnly = true;
                this.thresholdBBTextBox.FontWeight = FontWeights.Bold;
                this.volumeBBTextBox.FontWeight = FontWeights.Bold;
                this.movingAvgBBTextBox.FontWeight = FontWeights.Bold;
                this.stdDevBBTextBox.FontWeight = FontWeights.Bold;

            }

            //BollingerBandsTimer.Start();
            // And start it        
            BollingerBandsTimerTest.Enabled = true;
        }

        private void pauseResumeBBButton_Click(object sender, RoutedEventArgs e)
        {
            if (this.pauseResumeBBButton.Content.Equals("Resume"))
            {
                this.pauseResumeBBButton.Content = "Pause";
            }
            else
            {
                this.pauseResumeBBButton.Content = "Resume";
            }
        }

        private void deactivateBBButton_Click(object sender, RoutedEventArgs e)
        {
            SelectedStrategyStock = this.strategyStockComboBox.SelectedItem as Position;
            SelectedBollingerBandsStrategy = this.BollingerBandsStrategies.Where(b => b.Ticker == SelectedStrategyStock.Ticker).FirstOrDefault() as BollingerBandsStrategy;

            this.BollingerBandsViewList.Remove(this.BollingerBandsViewList.Where(b => b.StrategyBollingerBands.Ticker == SelectedBollingerBandsStrategy.Ticker).FirstOrDefault());
            this.BollingerBandsStrategies.Remove(this.BollingerBandsStrategies.Where(b => b.Ticker == SelectedBollingerBandsStrategy.Ticker).FirstOrDefault());
            this.bBStrategyComboBox.Items.Refresh();
            if (BollingerBandsStrategies.Count > 0)
            {
                this.SelectedBollingerBandsStrategy = BollingerBandsStrategies.FirstOrDefault();
                this.bollingerBandsChart.DataContext = BollingerBandsViewList.Where(b => b.StrategyBollingerBands.Ticker == SelectedBollingerBandsStrategy.Ticker).FirstOrDefault();
                this.bBStrategyComboBox.ItemsSource = BollingerBandsStrategies;
                this.bBStrategyComboBox.SelectedItem = this.SelectedBollingerBandsStrategy;
            }
            else
            {
                this.bollingerBandsChart.DataContext = null;
            }

            this.activateBBButton.IsEnabled = true;
            this.pauseResumeBBButton.IsEnabled = false;
            this.deactivateBBButton.IsEnabled = false;

            this.thresholdBBTextBox.IsReadOnly = false;
            this.volumeBBTextBox.IsReadOnly = false;
            this.movingAvgBBTextBox.IsReadOnly = false;
            this.stdDevBBTextBox.IsReadOnly = false;
            this.thresholdBBTextBox.FontWeight = FontWeights.Normal;
            this.volumeBBTextBox.FontWeight = FontWeights.Normal;
            this.movingAvgBBTextBox.FontWeight = FontWeights.Normal;
            this.stdDevBBTextBox.FontWeight = FontWeights.Normal;

            //BollingerBandsTimer.Stop();
            BollingerBandsTimerTest.Stop();
        }

        private void bBStrategyComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var comboBox = sender as ComboBox;
            BollingerBandsStrategy sBollingerBandsStrategy = comboBox.SelectedItem as BollingerBandsStrategy;
            SelectedBollingerBandsStrategy = sBollingerBandsStrategy as BollingerBandsStrategy;

            if (SelectedBollingerBandsStrategy != null)
            {
                this.strategyStockLabel.DataContext = SelectedStrategyStock;
                this.bBStrategyComboBox.ItemsSource = BollingerBandsStrategies;
                this.bollingerBandsChart.DataContext = BollingerBandsViewList.Where(b => b.StrategyBollingerBands.Ticker == SelectedBollingerBandsStrategy.Ticker).FirstOrDefault();
            }

        }

        #region TODELETE Performance, Add Stocks, Handling Event from Add Stock Window
        //private void addStockButton_Click(object sender, RoutedEventArgs e)
        //{
        //    //if (AddStockWindow == null)
        //    //{
        //    //    AddStockWindow = new AddStockWindow();
        //    //    AddStockWindow.DataContext = this;
        //    //    AddStockWindow.ListLiveStock = this.StockListing;
        //    //    AddStockWindow.addStockListBox.ItemsSource = AddStockWindow.ListLiveStock;

        //    //    SelectedAddStock = StockListing.FirstOrDefault();
        //    //    AddStockWindow.LiveFeedAdd = this.SelectedAddStock;
        //    //    AddStockWindow.addStockListBox.SelectedItem = AddStockWindow.StockToAdd;

        //    //    LivedataAddList = new List<Livedata>();
        //    //    for (int i = 0; i < StockListing.Count; i++)
        //    //    {
        //    //        LivedataAddList.Add(new Livedata(StockListing[i]));
        //    //    }
        //    //    var chartStockLive = from l in LivedataAddList
        //    //                         where l.SLiveFeed.Ticker == SelectedLiveStock.Ticker
        //    //                         select l as Livedata;
        //    //    if (chartStockLive != null)
        //    //    {
        //    //        AddStockWindow.LivedataToAddList = this.LivedataAddList;
        //    //        AddStockWindow.livedataChartAdd.DataContext = AddStockWindow.LivedataToAddList;
        //    //    }

        //    //    AddStockWindow.Owner = this;
        //    //    AddStockWindow.WindowStartupLocation = WindowStartupLocation.CenterOwner;
        //    //    this.ToAddStock = AddStockWindow.StockToAdd;

        //    //    if (this.AddStockWindow != null)
        //    //    {
        //    //        AddStockWindow.Show();
        //    //        this.AddStockWindow.addStockButton.Click += addStockToPortfolio_Click;
        //    //        this.AddStockWindow.Closed += AddStockWindow_Closed;
        //    //    }
        //    //    else
        //    //    {
        //    //        MessageBox.Show("Unknown error occured");
        //    //    }
        //    //}
        //    //else if (AddStockWindow != null && !AddStockWindow.IsActive)
        //    //{
        //    //    AddStockWindow.Activate();
        //    //}

        //}

        //private void addStockToPortfolio_Click(object sender, RoutedEventArgs e)
        //{
        //    this.ToAddStock = this.AddStockWindow.StockToAdd;
        //    var checkStockExist = from s in this.PortfolioPositions
        //                          where s.Ticker == this.ToAddStock.Ticker
        //                          select s as Position;
        //    if (checkStockExist.FirstOrDefault() != null)
        //    {
        //        this.PortfolioPositions.Where(stock => stock.Ticker == checkStockExist.FirstOrDefault().Ticker).FirstOrDefault().SharesProfitLoss += this.ToAddStock.SharesProfitLoss;
        //    }
        //    else
        //    {
        //        this.PortfolioPositions.Insert(0, this.ToAddStock);
        //    }
        //    this.portfolioDataGrid.Items.Refresh();
        //    this.livedataComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();
        //    //this.performanceComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();

        //}

        //private void performanceComboBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        //{
        //    var comboBox = sender as ComboBox;
        //    Position sPosition = comboBox.SelectedItem as Position;
        //    SelectedPerfStock = sPosition as Position;

        //    if (SelectedPerfStock == null)
        //    {
        //        SelectedPerfStock = PortfolioPositions.FirstOrDefault();
        //        //this.performanceComboBox.SelectedItem = PortfolioPositions.FirstOrDefault();
        //    }
        //    if (Performance != null)
        //    {
        //        Performance.SeriesCollection.Clear();

        //        ChartValues<double> cv = new ChartValues<double>();

        //        if (SelectedPerfStock != null)
        //        {
        //            LineSeries ls = new LineSeries
        //            {
        //                Title = SelectedPerfStock.Ticker,
        //                Values = cv,
        //                LineSmoothness = 0,
        //                StrokeThickness = 2,
        //                PointGeometrySize = 5
        //            };

        //            cv.Add(SelectedPerfStock.SharesProfitLoss);
        //            cv.Add(SelectedPerfStock.SharesProfitLoss);
        //            cv.Add(SelectedPerfStock.SharesProfitLoss);
        //            cv.Add(SelectedPerfStock.SharesProfitLoss);
        //            cv.Add(SelectedPerfStock.SharesProfitLoss);
        //            Performance.SeriesCollection.Add(ls);
        //        }

        //        //this.performanceChart.DataContext = Performance;
        //        //this.stockSymbolLabel.DataContext = SelectedPerfStock;
        //    }
        //}
        #endregion

    }
}
