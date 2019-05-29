require('dotenv').config();

const webpack = require('webpack');
const autoprefixer = require('autoprefixer');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

const proxy = {};
proxy[`/${process.env.API_PATH}`] = process.env.DEV_SERVER_PROXY;

module.exports = env => ({
  devtool: 'eval',
  mode: 'dev',
  entry: ['@babel/polyfill', './src'],
  output: {
    path: `${__dirname}/dist`,
    filename: '[name].js',
    publicPath: '/',
  },
  module: {
    rules: [
      {
        enforce: 'pre',
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: 'eslint-loader',
        options: {
          emitWarning: env === 'dev',
        },
      },
      {
        test: /\.js$|\.jsx?$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
      },
      {
        test: /\.css$|\.s[ac]ss$/,
        use: [
          'style-loader',
          'css-loader',
          {
            loader: 'postcss-loader',
            options: {
              sourceMap: true,
              plugins: () => [autoprefixer],
            },
          },
          {
            loader: 'sass-loader',
            options: {
              sourceMap: true,
            },
          },
        ],
      },
      {
        test: /\.jpe?g$|\.gif$|\.png$|\.svg$|\.eot$|\.woff$|\.ttf$/,
        loader: 'url-loader',
        options: {
          limit: '10000',
          name: '[name].[hash:5].[ext]',
        },
      },
    ],
  },
  target: 'web',
  devServer: {
    proxy,
    host: '0.0.0.0',
    port: process.env.DEV_SERVER_PORT,
    historyApiFallback: true,
    hot: true,
  },
  resolve: {
    modules: ['node_modules', 'material_kit'],
    extensions: ['.js', '.jsx', '.scss'],
  },
  plugins: [
    new webpack.EnvironmentPlugin(Object.keys(process.env)),
    new CleanWebpackPlugin({
      verbose: env === 'prod',
      dry: env === 'dev',
    }),
    new HtmlWebpackPlugin({
      template: './src/index.html',
      hash: env === 'prod',
    }),
    new MiniCssExtractPlugin({
      filename: env === 'dev' ? '[name].css' : '[name].[hash].css',
      chunkFilename: env === 'dev' ? '[id].css' : '[id].[hash].css',
    }),
  ],
  optimization: {
    splitChunks: {
      cacheGroups: {
        commons: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendors',
          chunks: 'all',
        },
      },
    },
    minimizer: [new UglifyJsPlugin({
      uglifyOptions: {
        output: {
          comments: false,
        },
      },
    })],
  },
});
