import React from 'react';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core
import TableCard from '../components/commons/TableCard';
import TableBody from '../components/resource/Body';
import SearchBar from '../components/resource/Tool';
import Edit from '../components/resource/Edit';

// static
import ObjectStatus from '../constants/ObjectStatus';
import resourcePageStyle from '../styles/jss/containers/resourcePageStyle';

@inject('resource')
@observer
class ResourcePage extends React.Component {
  componentDidMount() {
    const { resource } = this.props;
    resource.fetchPage();
  }

  render() {
    const { resource } = this.props;
    return (
      <TableCard
        cardTitle="Resource"
        cardDescription="Access resource"
        tableBody={<TableBody content={resource.content} />}
        searchBar={<SearchBar array={ObjectStatus.array} store={resource} />}
        edit={<Edit handleModal={resource.handleModal} modalOpen={resource.modalOpen} />}
        store={resource}
      />
    );
  }
}

ResourcePage.wrappedComponent.propTypes = {
  resource: PropTypes.observableObject.isRequired,
};

export default withStyles(resourcePageStyle)(ResourcePage);
