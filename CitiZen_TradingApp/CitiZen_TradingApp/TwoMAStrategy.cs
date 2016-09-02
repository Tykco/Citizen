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
    public class TwoMAStrategy : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        [DataMember(Name = "ticker")]
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

        [DataMember(Name = "longMa")]
        private double longMAPrice;
        public double LongMAPrice
        {
            get { return longMAPrice; }
            set
            {
                longMAPrice = value;
                OnPropertyChanged("LongMAPrice");
            }
        }

        [DataMember(Name = "shortMa")]
        private double shortMAPrice;
        public double ShortMAPrice
        {
            get { return shortMAPrice; }
            set
            {
                shortMAPrice = value;
                OnPropertyChanged("ShortMAPrice");
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

        private int longMA;
        public int LongMA
        {
            get { return longMA; }
            set
            {
                longMA = value;
                OnPropertyChanged("LongMA");
            }
        }

        private int shortMA;
        public int ShortMA
        {
            get { return shortMA; }
            set
            {
                shortMA = value;
                OnPropertyChanged("ShortMA");
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
