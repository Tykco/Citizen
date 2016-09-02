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
    public class LiveFeed : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
            {
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        //[DataMember(Name = "ticker")]
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

        [DataMember(Name = "liveId")]
        private int liveId;
        public int LiveId
        {
            get { return liveId; }
            set
            {
                liveId = value;
                OnPropertyChanged("LiveId");
            }
        }

        [DataMember(Name = "open")]
        private double open;
        public double Open
        {
            get { return open; }
            set
            {
                open = value;
                OnPropertyChanged("Open");
            }
        }

        [DataMember(Name = "price")]
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

        [DataMember(Name = "ask")]
        private double ask;
        public double Ask
        {
            get { return ask; }
            set
            {
                ask = value;
                OnPropertyChanged("Ask");
            }
        }

        [DataMember(Name = "bid")]
        private double bid;
        public double Bid
        {
            get { return bid; }
            set
            {
                bid = value;
                OnPropertyChanged("Bid");
            }
        }

        [DataMember(Name = "stock")]
        private Position stock;
        public Position Stock
        {
            get { return stock; }
            set
            {
                stock = value;
                OnPropertyChanged("Stock");
            }
        }

    }
}
