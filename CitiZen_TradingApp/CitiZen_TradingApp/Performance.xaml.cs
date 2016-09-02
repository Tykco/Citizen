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

namespace CitiZen_TradingApp
{
    /// <summary>
    /// Interaction logic for Performance.xaml
    /// </summary>
    public partial class Performance : UserControl, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        public string[] Labels { get; set; }
        public Func<double, string> YFormatter { get; set; }

        private SeriesCollection seriesCollection;
        public SeriesCollection SeriesCollection
        {
            get { return seriesCollection; }
            set
            {
                seriesCollection = value;
                OnPropertyChanged("SeriesCollection");
            }
        }

        private Position currStock;
        public Position CurrStock
        {
            get { return currStock; }
            set
            {
                currStock = value;
                OnPropertyChanged("CurrStock");
            }
        }

        public Performance()
        {
            InitializeComponent();
            this.SeriesCollection = new SeriesCollection();
                
            Labels = new[] {
                                string.Format("{0}:00", (DateTime.Now.Hour-4).ToString()),
                                string.Format("{0}:00", (DateTime.Now.Hour-3).ToString()),
                                string.Format("{0}:00", (DateTime.Now.Hour-2).ToString()),
                                string.Format("{0}:00", (DateTime.Now.Hour-1).ToString()),
                                string.Format("{0}:00", (DateTime.Now.Hour).ToString())
                            };
        }
    }
}
