using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.Serialization.Json;
using System.Text;

using System.Net.Http;
using System.Net.Http.Headers;

namespace CitiZen_TradingApp
{
    public class RESTService
    {
        public static string baseUrl = "http://localhost:8080/citizen_app/rest/";
        //public List<LiveFeed> ReturnStockList { get; set; }
        public Portfolio ReturnPortfolio { get; set; }
        public List<LiveFeed> ReturnLiveFeed { get; set; }
        public List<Position> ReturnPositions { get; set; }
        public List<Order> ReturnOrders { get; set; }
        //public TwoMAStrategy ReturnTMA { get; set; }
        //public BollingerBandsStrategy ReturnBB { get; set; }
        public Position ReturnPosition { get; set; }
        public List<TwoMAStrategy> ReturnTMAFeed { get; set; }
        public List<BollingerBandsStrategy> ReturnBBFeed { get; set; }

        public bool GetUserPortfolio(string username)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("portfolio/" + username).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Portfolio[]));
                    Portfolio[] p = serializer.ReadObject(ms) as Portfolio[];

                    if (p != null)
                    {
                        ReturnPortfolio = p.ToList().FirstOrDefault();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool InitLiveFeed(string tickers)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    try
                    {

                        HttpResponseMessage response = client.GetAsync("init/" + tickers + "/start").Result;
                        response.EnsureSuccessStatusCode();
                    }
                    catch (Exception ex)
                    {
                        return false;
                    }

                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool GetLiveFeed(string ticker, int liveId)
        {
            ticker = "goog";
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("livedata/" + ticker + "?liveid=" + liveId).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(LiveFeed[]));
                    LiveFeed[] p = serializer.ReadObject(ms) as LiveFeed[];

                    if (p != null)
                    {
                        ReturnLiveFeed = p.ToList();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool GetPositions(int pf_id)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("position?pfid=" + pf_id).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Position[]));
                    Position[] p = serializer.ReadObject(ms) as Position[];

                    if (p != null)
                    {
                        ReturnPositions = p.ToList();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool GetOrdersByPortfolio(int pf_id)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("trade?pfid=" + pf_id).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Order[]));
                    Order[] o = serializer.ReadObject(ms) as Order[];

                    if (o != null)
                    {
                        ReturnOrders = o.ToList();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool GetOrdersByPositions(int pf_id, string ticker)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("trade?pfid=" + pf_id + "&ticker=" + ticker).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Order[]));
                    Order[] o = serializer.ReadObject(ms) as Order[];

                    if (o != null)
                    {
                        ReturnOrders = o.ToList();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool StartTMA(string ticker, double longMA, double shortMA, int quantity, double threshold, int pfId)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("strat/" + pfId + "/" + ticker + "/tma/start?longma=" + longMA + "&shortMA=" + shortMA + "&quantity=" + quantity + "&threshold=" + threshold).Result;
                    response.EnsureSuccessStatusCode();
                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool StartBB(string ticker, double bbMA, int quantity, double threshold, double stdDiv, int pfId)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("strat/" + pfId + "/" + ticker + "/bb/start?quantity=" + quantity + "&threshold=" + threshold + "&stdDiv=" + stdDiv + "&bbma=" + bbMA).Result;
                    response.EnsureSuccessStatusCode();
                    return true;
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        public bool GetStrategyValues(string ticker, string strategy, int liveId)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("stratfeed/" + ticker + "/" + strategy + "?liveid=" + liveId).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    
                    if (strategy.Equals("tma"))
                    {
                        DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(TwoMAStrategy[]));
                        TwoMAStrategy[] s = serializer.ReadObject(ms) as TwoMAStrategy[];
                        if (s != null)
                        {
                            ReturnTMAFeed = s.ToList();
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else if (strategy.Equals("bb"))
                    {
                        DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(BollingerBandsStrategy[]));
                        BollingerBandsStrategy[] s = serializer.ReadObject(ms) as BollingerBandsStrategy[];
                        if (s != null)
                        {
                            ReturnBBFeed = s.ToList();
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }

                   
                }
                catch (Exception ex)
                {
                    return false;
                }
            }
        }

        //Unsure
        public bool addStock(string ticker)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("CitiZenWeb/rest/add/" + ticker).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Position));
                    Position p = serializer.ReadObject(ms) as Position;

                    if (p != null)
                    {
                        ReturnPosition = p;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }
        //Unsure
        public bool removeStock(string ticker)
        {
            using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
            {
                client.BaseAddress = new Uri(baseUrl);
                try
                {
                    HttpResponseMessage response = client.GetAsync("CitiZenWeb/rest/remove/" + ticker).Result;
                    response.EnsureSuccessStatusCode();
                    string result = response.Content.ReadAsStringAsync().Result;

                    MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));


                    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Position));
                    Position p = serializer.ReadObject(ms) as Position;

                    if (p != null)
                    {
                        ReturnPosition = p;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    return false;
                }

            }
        }

        ////for testing
        //public bool GetAllStocks()
        //{
        //    using (var client = new HttpClient(new HttpClientHandler { AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate }))
        //    {
        //        client.BaseAddress = new Uri(baseUrl);
        //        try
        //        {
        //            HttpResponseMessage response = client.GetAsync("CitiZenWeb/rest/stock").Result;
        //            response.EnsureSuccessStatusCode();
        //            string result = response.Content.ReadAsStringAsync().Result;

        //            MemoryStream ms = new MemoryStream(System.Text.ASCIIEncoding.ASCII.GetBytes(result));
        //            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(LiveFeed[]));
        //            LiveFeed[] validateStock = serializer.ReadObject(ms) as LiveFeed[];

        //            if (validateStock != null)
        //            {
        //                ReturnStockList = validateStock.ToList();
        //                return true;
        //            }
        //            else
        //            {
        //                return false;
        //            }
        //        }
        //        catch (Exception ex)
        //        {
        //            return false;
        //        }

        //    }
        //}  

    }
}