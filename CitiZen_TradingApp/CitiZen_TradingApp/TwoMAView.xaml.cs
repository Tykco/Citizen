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
using LiveCharts.Configurations;
using System.ComponentModel;
using System.Windows.Threading;

namespace CitiZen_TradingApp
{
    /// <summary>
    /// Interaction logic for TwoMAView.xaml
    /// </summary>
    public partial class TwoMAView : UserControl, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        private double axisMax;
        public double AxisMax
        {
            get { return axisMax; }
            set
            {
                axisMax = value;
                OnPropertyChanged("AxisMax");
            }
        }
        
        private double axisMin;
        public double AxisMin
        {
            get { return axisMin; }
            set
            {
                axisMin = value;
                OnPropertyChanged("AxisMin");
            }
        }

        public ChartValues<MeasureModel> ChartValuesPrice { get; set; }
        public ChartValues<MeasureModel> ChartValuesLongMA { get; set; }
        public ChartValues<MeasureModel> ChartValuesShortMA { get; set; }

        public Func<double, string> DateTimeFormatter { get; set; }
        public double AxisStep { get; set; }

        public DispatcherTimer Timer { get; set; }
        public bool IsDataInjectionRunning { get; set; }

        private TwoMAStrategy strategyTwoMA;
        public TwoMAStrategy StrategyTwoMA
        {
            get { return strategyTwoMA; }
            set
            {
                strategyTwoMA = value;
                OnPropertyChanged("StrategyTwoMA");
            }
        }

        public TwoMAView()
        {
            InitializeComponent();
        }

        public TwoMAView(TwoMAStrategy currTwoMAStrategy)
        {
            InitializeComponent();
            this.StrategyTwoMA = currTwoMAStrategy;

            var mapper = Mappers.Xy<MeasureModel>()
                .X(model => model.DateTime.Ticks)   //use DateTime.Ticks as X
                .Y(model => model.Value);           //use the value property as Y

            //lets save the mapper globally.
            Charting.For<MeasureModel>(mapper);

            //the values property will store our values array
            ChartValuesPrice = new ChartValues<MeasureModel>();
            ChartValuesLongMA = new ChartValues<MeasureModel>();
            ChartValuesShortMA = new ChartValues<MeasureModel>();

            //lets set how to display the X Labels
            DateTimeFormatter = value => new DateTime((long)value).ToString("hh:mm:ss");

            AxisStep = TimeSpan.FromSeconds(1).Ticks;
            SetAxisLimits(DateTime.Now);

            //The next code simulates data changes every 1000 ms
            Timer = new DispatcherTimer
            {
                Interval = TimeSpan.FromMilliseconds(1000)
            };
            Timer.Tick += TimerOnTick;
            IsDataInjectionRunning = false;

            DataContext = this;

            if (IsDataInjectionRunning)
            {
                Timer.Stop();
                IsDataInjectionRunning = false;
            }
            else
            {
                Timer.Start();
                IsDataInjectionRunning = true;
            }

            //this.Foreground = new SolidColorBrush(Colors.Red);
            //this.Background = new SolidColorBrush(Colors.Yellow);

        }

        public void TimerOnTick(object sender, EventArgs eventArgs)
        {
            var now = DateTime.Now;

            double liveValuePrice = 0;
            double liveValueLongMA = 0;
            double liveValueShortMA = 0;

            if (StrategyTwoMA != null)
            {
                liveValuePrice = StrategyTwoMA.Price;
                liveValueLongMA = StrategyTwoMA.LongMAPrice;
                liveValueShortMA = StrategyTwoMA.ShortMAPrice;
            }

            ChartValuesPrice.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValuePrice
            });

            ChartValuesLongMA.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValueLongMA
            });

            ChartValuesShortMA.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValueShortMA
            });

            SetAxisLimits(now);

            //lets only use the last 100 values
            if (ChartValuesPrice.Count > 100)
            {
                ChartValuesPrice.RemoveAt(0);
            }
           
            if (ChartValuesLongMA.Count > 100)
            {
                ChartValuesLongMA.RemoveAt(0);
            }
            
            if (ChartValuesShortMA.Count > 100)
            {
                ChartValuesShortMA.RemoveAt(0);
            }
            
        }

        public void SetAxisLimits(DateTime now)
        {
            AxisMax = now.Ticks + TimeSpan.FromSeconds(1).Ticks; // lets force the axis to be 100ms ahead (100)
            AxisMin = now.Ticks - TimeSpan.FromSeconds(8).Ticks; //we only care about the last 8 seconds (50)
        }

    }
}
