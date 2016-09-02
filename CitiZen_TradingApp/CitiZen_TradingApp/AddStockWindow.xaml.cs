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
using System.Windows.Shapes;
using LiveCharts;
using LiveCharts.Wpf;
using System.ComponentModel;
using System.Windows.Threading;

namespace CitiZen_TradingApp
{
    /// <summary>
    /// Interaction logic for AddStock.xaml
    /// </summary>
    public partial class AddStockWindow : Window, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        private List<Livedata> livedataToAddList;
        public List<Livedata> LivedataToAddList
        {
            get { return livedataToAddList; }
            set
            {
                livedataToAddList = value;
                OnPropertyChanged("LivedataToAddList");
            }
        }

        private List<LiveFeed> listLiveStock;
        public List<LiveFeed> ListLiveStock
        {
            get { return listLiveStock; }
            set
            {
                listLiveStock = value;
                OnPropertyChanged("ListLiveStock");
            }
        }

        private LiveFeed liveFeedAdd;
        public LiveFeed LiveFeedAdd
        {
            get { return liveFeedAdd; }
            set
            {
                liveFeedAdd = value;
                OnPropertyChanged("LiveFeedAdd");
            }
        }

        private Position stockToAdd;
        public Position StockToAdd
        {
            get { return stockToAdd; }
            set
            {
                stockToAdd = value;
                OnPropertyChanged("StockToAdd");
            }
        }

        public AddStockWindow()
        {
            InitializeComponent();
            StockToAdd = new Position { CompanyName = "ERROR", Ticker = "ERROR", Shares = 0, SharesProfitLoss = 0 };
        }

        private void addStockButton_Click(object sender, RoutedEventArgs e)
        {
            if(this.addStockListBox.SelectedItem != null)
            {
                int volume;
                int.TryParse(this.addStockVolumeTextBox.Text, out volume);

                if(volume != 0)
                {
                    LiveFeed sStockFeed = this.addStockListBox.SelectedItem as LiveFeed;
                    LiveFeedAdd = sStockFeed as LiveFeed;
                    StockToAdd = new Position { Ticker = LiveFeedAdd.Ticker, CompanyName = LiveFeedAdd.Ticker, Shares = volume, SharesProfitLoss = LiveFeedAdd.Price };

                    MessageBox.Show("Stock successfully added", "Stock Added", MessageBoxButton.OK, MessageBoxImage.Information);
                }
                else
                {
                    MessageBox.Show("Unknown error occured");
                }
            }
        }

        private void addStockListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var listBox = sender as ListBox;
            LiveFeed sLiveFeed = listBox.SelectedItem as LiveFeed;
            this.LiveFeedAdd = sLiveFeed as LiveFeed;

            if (LivedataToAddList != null)
            {
                //LivedataToAddList.Where(l => l.SStock.Ticker == SAddStock.Ticker).Equals(SAddStock as Stock);

                this.livedataChartAdd.DataContext = LivedataToAddList.Where(l => l.SLiveFeed.Ticker == StockToAdd.Ticker);
            }
        }

        private void TextBox_PreviewTextInput(object sender, TextCompositionEventArgs e)
        {
            int num;
            if (!int.TryParse(e.Text, out num))
            {
                e.Handled = true;
            }
        }

        private void Window_Closing(object sender, CancelEventArgs e)
        {
            //stub
        }
    }
}
