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
    /// Interaction logic for BollingerBandsView.xaml
    /// </summary>
    public partial class BollingerBandsView : UserControl, INotifyPropertyChanged
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
        public ChartValues<MeasureModel> ChartValuesUpperBand { get; set; }
        public ChartValues<MeasureModel> ChartValuesMiddleBand { get; set; }
        public ChartValues<MeasureModel> ChartValuesLowerBand { get; set; }

        public Func<double, string> DateTimeFormatter { get; set; }
        public double AxisStep { get; set; }

        public DispatcherTimer Timer { get; set; }
        public bool IsDataInjectionRunning { get; set; }

        private BollingerBandsStrategy strategyBollingerBands;
        public BollingerBandsStrategy StrategyBollingerBands
        {
            get { return strategyBollingerBands; }
            set
            {
                strategyBollingerBands = value;
                OnPropertyChanged("StrategyBollingerBands");
            }
        }

        public BollingerBandsView()
        {
            InitializeComponent();
        }

        public BollingerBandsView(BollingerBandsStrategy currBollingerBandsStrategy)
        {
            InitializeComponent();
            this.StrategyBollingerBands = currBollingerBandsStrategy;

            var mapper = Mappers.Xy<MeasureModel>()
                .X(model => model.DateTime.Ticks)   //use DateTime.Ticks as X
                .Y(model => model.Value);           //use the value property as Y

            //lets save the mapper globally.
            Charting.For<MeasureModel>(mapper);

            //the values property will store our values array
            ChartValuesPrice = new ChartValues<MeasureModel>();
            ChartValuesUpperBand = new ChartValues<MeasureModel>();
            ChartValuesMiddleBand = new ChartValues<MeasureModel>();
            ChartValuesLowerBand = new ChartValues<MeasureModel>();

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
            double liveValueUpperBand = 0;
            double liveValueMiddleBand = 0;
            double liveValueLowerBand = 0;

            if (StrategyBollingerBands != null)
            {
                liveValuePrice = StrategyBollingerBands.Price;
                liveValueUpperBand = StrategyBollingerBands.UpperBand;
                liveValueMiddleBand = StrategyBollingerBands.MiddleBand;
                liveValueLowerBand = StrategyBollingerBands.LowerBand;
            }

            ChartValuesPrice.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValuePrice
            });

            ChartValuesUpperBand.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValueUpperBand
            });

            ChartValuesMiddleBand.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValueMiddleBand
            });

            ChartValuesLowerBand.Add(new MeasureModel
            {
                DateTime = now,
                Value = liveValueLowerBand
            });

            SetAxisLimits(now);

            //lets only use the last 100 values
            if (ChartValuesPrice.Count > 100)
            {
                ChartValuesPrice.RemoveAt(0);
            }
           
            if (ChartValuesUpperBand.Count > 100)
            {
                ChartValuesUpperBand.RemoveAt(0);
            }

            if (ChartValuesMiddleBand.Count > 100)
            {
                ChartValuesMiddleBand.RemoveAt(0);
            }

            if (ChartValuesLowerBand.Count > 100)
            {
                ChartValuesLowerBand.RemoveAt(0);
            }

        }

        public void SetAxisLimits(DateTime now)
        {
            AxisMax = now.Ticks + TimeSpan.FromSeconds(1).Ticks; // lets force the axis to be 100ms ahead (100)
            AxisMin = now.Ticks - TimeSpan.FromSeconds(8).Ticks; //we only care about the last 8 seconds (50)
        }

    }
}
