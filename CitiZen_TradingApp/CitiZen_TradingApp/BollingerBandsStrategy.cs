using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using System.Runtime.Serialization;

namespace CitiZen_TradingApp
{
    [DataContract]
    public class BollingerBandsStrategy : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        private string ticker;
        public string Ticker
        {
            get { return ticker; }
            set
            {
                ticker = value;
                OnPropertyChanged("Ticker");
            }
        }

        private double price;
        public double Price
        {
            get { return price; }
            set
            {
                price = value;
                OnPropertyChanged("Price");
            }
        }

        [DataMember(Name = "upperBand")]
        private double upperBand;
        public double UpperBand
        {
            get { return upperBand; }
            set
            {
                upperBand = value;
                OnPropertyChanged("UpperBand");
            }
        }

        [DataMember(Name = "middleBand")]
        private double middleBand;
        public double MiddleBand
        {
            get { return middleBand; }
            set
            {
                middleBand = value;
                OnPropertyChanged("MiddleBand");
            }
        }

        [DataMember(Name = "lowerBand")]
        private double lowerBand;
        public double LowerBand
        {
            get { return lowerBand; }
            set
            {
                lowerBand = value;
                OnPropertyChanged("LowerBand");
            }
        }

        [DataMember(Name = "livemarketdata")]
        private LiveFeed liveMarketData;
        public LiveFeed LiveMarketData
        {
            get { return liveMarketData; }
            set
            {
                liveMarketData = value;
                OnPropertyChanged("LiveMarketData");
            }
        }

        private double movingAvg;
        public double MovingAvg
        {
            get { return movingAvg; }
            set
            {
                movingAvg = value;
                OnPropertyChanged("MovingAvg");
            }
        }

        private double threshold;
        public double Threshold
        {
            get { return threshold; }
            set
            {
                threshold = value;
                OnPropertyChanged("Threshold");
            }
        }

        private int volume;
        public int Volume
        {
            get { return volume; }
            set
            {
                volume = value;
                OnPropertyChanged("Volume");
            }
        }

        private double stdDev;
        public double StdDev
        {
            get { return stdDev; }
            set
            {
                stdDev = value;
                OnPropertyChanged("StdDev");
            }
        }

        private bool isNotActivated;
        public bool IsNotActivated
        {
            get { return isNotActivated; }
            set
            {
                isNotActivated = value;
                OnPropertyChanged("IsNotActivated");
            }
        }

    }
}
