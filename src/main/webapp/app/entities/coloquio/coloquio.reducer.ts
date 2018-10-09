import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IColoquio, defaultValue } from 'app/shared/model/coloquio.model';

export const ACTION_TYPES = {
  FETCH_COLOQUIO_LIST: 'coloquio/FETCH_COLOQUIO_LIST',
  FETCH_COLOQUIO: 'coloquio/FETCH_COLOQUIO',
  CREATE_COLOQUIO: 'coloquio/CREATE_COLOQUIO',
  UPDATE_COLOQUIO: 'coloquio/UPDATE_COLOQUIO',
  DELETE_COLOQUIO: 'coloquio/DELETE_COLOQUIO',
  RESET: 'coloquio/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IColoquio>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ColoquioState = Readonly<typeof initialState>;

// Reducer

export default (state: ColoquioState = initialState, action): ColoquioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COLOQUIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLOQUIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COLOQUIO):
    case REQUEST(ACTION_TYPES.UPDATE_COLOQUIO):
    case REQUEST(ACTION_TYPES.DELETE_COLOQUIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COLOQUIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLOQUIO):
    case FAILURE(ACTION_TYPES.CREATE_COLOQUIO):
    case FAILURE(ACTION_TYPES.UPDATE_COLOQUIO):
    case FAILURE(ACTION_TYPES.DELETE_COLOQUIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLOQUIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COLOQUIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLOQUIO):
    case SUCCESS(ACTION_TYPES.UPDATE_COLOQUIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLOQUIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/coloquios';

// Actions

export const getEntities: ICrudGetAllAction<IColoquio> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COLOQUIO_LIST,
  payload: axios.get<IColoquio>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IColoquio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLOQUIO,
    payload: axios.get<IColoquio>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IColoquio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLOQUIO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IColoquio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLOQUIO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IColoquio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLOQUIO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
